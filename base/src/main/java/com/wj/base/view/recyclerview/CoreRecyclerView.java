package com.wj.base.view.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;


import com.just.agentweb.LogUtils;
import com.wj.base.R;

import java.util.ArrayList;
import java.util.List;


/**
 * * notice:
 * 1 emptyView  ：A 同时配置3个条件  @id/emptyView &&isHeaderDividersEnabled = true &&setEmptyClickListener
 * B  直接设置 setEmptView
 * postion 注意 headview count postion+headviewCount = postion
 * <p/>
 * 2 其它同listView
 */
public class CoreRecyclerView extends RecyclerView {
    public static final int MAX_Y_OVERSCROLL_DISTANCE = 200;
    public static final int LAYOUT_MANAGER_TYPE_LINEAR = 0;
    public static final int LAYOUT_MANAGER_TYPE_GRID = 1;
    public static final int LAYOUT_MANAGER_TYPE_STAGGERED_GRID = 2;

    private static final int DEF_LAYOUT_MANAGER_TYPE = LAYOUT_MANAGER_TYPE_LINEAR;
    private static final int DEF_GRID_SPAN_COUNT = 2;
    private static final int DEF_LAYOUT_MANAGER_ORIENTATION = OrientationHelper.VERTICAL;
    private static final int DEF_DIVIDER_HEIGHT = 1;

    private List<View> mHeaderView = new ArrayList<View>();
    private List<View> mFooterView = new ArrayList<View>();
    private CoreWrapRecyclerViewAdapter mWrapCoreRecyclerViewAdapter;
    private Adapter mReqAdapter;
    private GridLayoutManager mCurGridLayoutManager;
    private CoreDefaultItemDecoration mFamiliarDefaultItemDecoration;

    private Drawable mVerticalDivider;
    private Drawable mHorizontalDivider;
    private int mVerticalDividerHeight;
    private int mHorizontalDividerHeight;
    private int mItemViewBothSidesMargin;
    private boolean isHeaderDividersEnabled = false;
    private boolean isFooterDividersEnabled = false;
    private boolean isDefaultItemDecoration = true;
    private int mEmptyViewResId;
    private View mEmptyView;
    private OnItemClickListener mTempOnItemClickListener;
    private OnItemLongClickListener mTempOnItemLongClickListener;
    private OnHeadViewBindViewHolderListener mTempOnHeadViewBindViewHolderListener;
    private OnFooterViewBindViewHolderListener mTempOnFooterViewBindViewHolderListener;
    private int mLayoutManagerType;
    private int mMaxYOverscrollDistance = 0;
    private OnClickListener mEmptyViewListener;
    private boolean hasShowEmptyView;
    private InterfaceSetAdapter mInterfaceSetAdapter;

    public CoreRecyclerView(Context context) {
        this(context, null);
    }

    public CoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CoreRecyclerView);
        Drawable divider = ta.getDrawable(R.styleable.CoreRecyclerView_frv_divider);
        int dividerHeight = (int) ta.getDimension(R.styleable.CoreRecyclerView_frv_dividerHeight, -1);
        mVerticalDivider = ta.getDrawable(R.styleable.CoreRecyclerView_frv_dividerVertical);
        mHorizontalDivider = ta.getDrawable(R.styleable.CoreRecyclerView_frv_dividerHorizontal);
        mVerticalDividerHeight = (int) ta.getDimension(R.styleable.CoreRecyclerView_frv_dividerVerticalHeight, -1);
        mHorizontalDividerHeight = (int) ta.getDimension(R.styleable.CoreRecyclerView_frv_dividerHorizontalHeight, -1);
        mItemViewBothSidesMargin = (int) ta.getDimension(R.styleable.CoreRecyclerView_frv_itemViewBothSidesMargin, 0);
        mEmptyViewResId = ta.getResourceId(R.styleable.CoreRecyclerView_frv_emptyView, -1);
        isHeaderDividersEnabled = ta.getBoolean(R.styleable.CoreRecyclerView_frv_headerDividersEnabled, false);
        isFooterDividersEnabled = ta.getBoolean(R.styleable.CoreRecyclerView_frv_footerDividersEnabled, false);
        if (ta.hasValue(R.styleable.CoreRecyclerView_frv_layoutManager)) {
            int layoutManagerType = ta.getInt(R.styleable.CoreRecyclerView_frv_layoutManager, DEF_LAYOUT_MANAGER_TYPE);
            int layoutManagerOrientation = ta.getInt(R.styleable.CoreRecyclerView_frv_layoutManagerOrientation, DEF_LAYOUT_MANAGER_ORIENTATION);
            boolean isReverseLayout = ta.getBoolean(R.styleable.CoreRecyclerView_frv_isReverseLayout, false);
            int gridSpanCount = ta.getInt(R.styleable.CoreRecyclerView_frv_spanCount, DEF_GRID_SPAN_COUNT);

            switch (layoutManagerType) {
                case LAYOUT_MANAGER_TYPE_LINEAR:
                    processGridDivider(divider, dividerHeight, false, layoutManagerOrientation);
                    setLayoutManager(new LinearLayoutManager(context, layoutManagerOrientation, isReverseLayout));
                    break;
                case LAYOUT_MANAGER_TYPE_GRID:
                    processGridDivider(divider, dividerHeight, false, layoutManagerOrientation);
                    setLayoutManager(new GridLayoutManager(context, gridSpanCount, layoutManagerOrientation, isReverseLayout));
                    break;
                case LAYOUT_MANAGER_TYPE_STAGGERED_GRID:
                    processGridDivider(divider, dividerHeight, false, layoutManagerOrientation);
                    final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(gridSpanCount, layoutManagerOrientation);
                    staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                    setLayoutManager(staggeredGridLayoutManager);
                    break;
            }
        }
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        final float density = metrics.density;

        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
        ta.recycle();
    }

    private void processGridDivider(Drawable divider, int dividerHeight, boolean isLinearLayoutManager, int layoutManagerOrientation) {
        if ((null == mVerticalDivider || null == mHorizontalDivider) && null != divider) {
            if (isLinearLayoutManager) {
                if (layoutManagerOrientation == OrientationHelper.VERTICAL && null == mHorizontalDivider) {
                    mHorizontalDivider = divider;
                } else if (layoutManagerOrientation == OrientationHelper.HORIZONTAL && null == mVerticalDivider) {
                    mVerticalDivider = divider;
                }
            } else {
                if (null == mVerticalDivider) {
                    mVerticalDivider = divider;
                }

                if (null == mHorizontalDivider) {
                    mHorizontalDivider = divider;
                }
            }
        }

        if (mVerticalDividerHeight > 0 && mHorizontalDividerHeight > 0) return;

        if (dividerHeight > 0) {
            if (isLinearLayoutManager) {
                if (layoutManagerOrientation == OrientationHelper.VERTICAL && mHorizontalDividerHeight <= 0) {
                    mHorizontalDividerHeight = dividerHeight;
                } else if (layoutManagerOrientation == OrientationHelper.HORIZONTAL && mVerticalDividerHeight <= 0) {
                    mVerticalDividerHeight = dividerHeight;
                }
            } else {
                if (mVerticalDividerHeight <= 0) {
                    mVerticalDividerHeight = dividerHeight;
                }

                if (mHorizontalDividerHeight <= 0) {
                    mHorizontalDividerHeight = dividerHeight;
                }
            }
        } else {
            if (isLinearLayoutManager) {
                if (layoutManagerOrientation == OrientationHelper.VERTICAL && mHorizontalDividerHeight <= 0) {
                    if (null != mHorizontalDivider) {
                        if (mHorizontalDivider.getIntrinsicHeight() > 0) {
                            mHorizontalDividerHeight = mHorizontalDivider.getIntrinsicHeight();
                        } else {
                            mHorizontalDividerHeight = DEF_DIVIDER_HEIGHT;
                        }
                    }
                } else if (layoutManagerOrientation == OrientationHelper.HORIZONTAL && mVerticalDividerHeight <= 0) {
                    if (null != mVerticalDivider) {
                        if (mVerticalDivider.getIntrinsicHeight() > 0) {
                            mVerticalDividerHeight = mVerticalDivider.getIntrinsicHeight();
                        } else {
                            mVerticalDividerHeight = DEF_DIVIDER_HEIGHT;
                        }
                    }
                }
            } else {
                if (mVerticalDividerHeight <= 0 && null != mVerticalDivider) {
                    if (mVerticalDivider.getIntrinsicHeight() > 0) {
                        mVerticalDividerHeight = mVerticalDivider.getIntrinsicHeight();
                    } else {
                        mVerticalDividerHeight = DEF_DIVIDER_HEIGHT;
                    }
                }

                if (mHorizontalDividerHeight <= 0 && null != mHorizontalDivider) {
                    if (mHorizontalDivider.getIntrinsicHeight() > 0) {
                        mHorizontalDividerHeight = mHorizontalDivider.getIntrinsicHeight();
                    } else {
                        mHorizontalDividerHeight = DEF_DIVIDER_HEIGHT;
                    }
                }
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
// Only once
        if (mEmptyViewResId != -1) {
            if (null != getParent()) {
                ViewGroup parentView = ((ViewGroup) getParent());
                View tempEmptyView1 = parentView.findViewById(mEmptyViewResId);

                if (null != tempEmptyView1) {
                    mEmptyView = tempEmptyView1;
                    parentView.removeView(tempEmptyView1);
                    mEmptyView.setVisibility(GONE);
                } else {
                    ViewParent pParentView = parentView.getParent();
                    if (null != pParentView && pParentView instanceof ViewGroup) {
                        View tempEmptyView2 = ((ViewGroup) pParentView).findViewById(mEmptyViewResId);
                        if (null != tempEmptyView2) {
                            mEmptyView = tempEmptyView2;
                            mEmptyView.setVisibility(GONE);
                            ((ViewGroup)pParentView).removeView(tempEmptyView2);
                        } else {
                            ViewParent ppParentView = pParentView.getParent();
                            View tempEmptyView3 = ((ViewGroup) ppParentView).findViewById(mEmptyViewResId);
                            if (null != tempEmptyView3) {
                                mEmptyView = tempEmptyView3;
                                mEmptyView.setVisibility(GONE);
                                ((ViewGroup)ppParentView).removeView(tempEmptyView3);
                            }
                        }
                    }
                }
            }
            mEmptyViewResId = -1;
        }
        if (null == adapter) {
            if (null != mReqAdapter) {
                mReqAdapter.unregisterAdapterDataObserver(mReqAdapterDataObserver);
                mReqAdapter = null;
                mWrapCoreRecyclerViewAdapter = null;

                processEmptyView();
            }

            return;
        }

        if (null != mReqAdapterDataObserver && null != mReqAdapter) {
            mReqAdapter.unregisterAdapterDataObserver(mReqAdapterDataObserver);
        }
        mReqAdapter = adapter;
        mWrapCoreRecyclerViewAdapter = new CoreWrapRecyclerViewAdapter(this, adapter, mHeaderView, mFooterView, mLayoutManagerType);

        mWrapCoreRecyclerViewAdapter.setOnItemClickListener(mTempOnItemClickListener);
        mWrapCoreRecyclerViewAdapter.setOnItemLongClickListener(mTempOnItemLongClickListener);
        mWrapCoreRecyclerViewAdapter.setOnHeadViewBindViewHolderListener(mTempOnHeadViewBindViewHolderListener);
        mWrapCoreRecyclerViewAdapter.setOnFooterViewBindViewHolderListener(mTempOnFooterViewBindViewHolderListener);

        mReqAdapter.registerAdapterDataObserver(mReqAdapterDataObserver);

        super.setAdapter(mWrapCoreRecyclerViewAdapter);
        processEmptyView();
        if (null!=mInterfaceSetAdapter)
            mInterfaceSetAdapter.onSetAdapter();

    }


    public void setInterfaceSetAdapter(InterfaceSetAdapter mInterfaceSetAdapter) {
        this.mInterfaceSetAdapter = mInterfaceSetAdapter;
    }

    /**
     * 测量emptyView
     */
    public void getEmptyViewHeight() {
       /*if (mEmptyViewHeight == 0) {
            mEmptyViewHeight = getMeasuredHeight();
        }
        int height = mEmptyViewHeight;
        for (int i = 0; i < mHeaderView.size(); i++) {
            View headView = mHeaderView.get(i);
            if (headView.isShown()) {
                height -= mHeaderView.get(i).getMeasuredHeight();
            }
        }
        for (int i = 0; i < mFooterView.size(); i++) {
            View footView = mFooterView.get(i);
            if (footView.isShown()) {
                height -= mFooterView.get(i).getMeasuredHeight();
            }
        }*/
        mEmptyView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);

        if (null == layout) return;

        if (layout instanceof GridLayoutManager) {
            mCurGridLayoutManager = ((GridLayoutManager) layout);
            mCurGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position < getHeaderViewsCount() || position >= mReqAdapter.getItemCount() + getHeaderViewsCount()) {
                        // header or footer span
                        return mCurGridLayoutManager.getSpanCount();
                    } else {
                        // default item span
                        return 1;
                    }
                }
            });

            mLayoutManagerType = LAYOUT_MANAGER_TYPE_GRID;
            setDivider(mVerticalDivider, mHorizontalDivider);
        } else if (layout instanceof StaggeredGridLayoutManager) {
            mLayoutManagerType = LAYOUT_MANAGER_TYPE_STAGGERED_GRID;
            setDivider(mVerticalDivider, mHorizontalDivider);
        } else if (layout instanceof LinearLayoutManager) {
            mLayoutManagerType = LAYOUT_MANAGER_TYPE_LINEAR;
            if (((LinearLayoutManager) layout).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                setDividerVertical(mVerticalDivider);
            } else {
                setDividerHorizontal(mHorizontalDivider);
            }
        }
    }

    @Override
    public void addItemDecoration(ItemDecoration decor) {
        if (null == decor) return;

        // remove default ItemDecoration
        if (null != mFamiliarDefaultItemDecoration) {
            removeItemDecoration(mFamiliarDefaultItemDecoration);
            mFamiliarDefaultItemDecoration = null;
        }

        isDefaultItemDecoration = false;

        super.addItemDecoration(decor);
    }

    private void addDefaultItemDecoration() {
        if (null != mFamiliarDefaultItemDecoration) {
            removeItemDecoration(mFamiliarDefaultItemDecoration);
            mFamiliarDefaultItemDecoration = null;
        }

        mFamiliarDefaultItemDecoration = new CoreDefaultItemDecoration(this, mVerticalDivider, mHorizontalDivider, mVerticalDividerHeight, mHorizontalDividerHeight);
        mFamiliarDefaultItemDecoration.setItemViewBothSidesMargin(mItemViewBothSidesMargin);
        mFamiliarDefaultItemDecoration.setHeaderDividersEnabled(isHeaderDividersEnabled);
        mFamiliarDefaultItemDecoration.setFooterDividersEnabled(isFooterDividersEnabled);
        super.addItemDecoration(mFamiliarDefaultItemDecoration);
    }

    /**
     * 由于emptyView 高度无法初始化
     * 先隐藏视图 待 测量好高度后再显示
     * <p/>
     * emptyView 作为 recycleView子试图处理
     * 也可以传统方式布局需设置mEmptyView = null
     * modified by qtl
     */
    private void processEmptyView() {
        LogUtils.e("processEmptyView","---");
        if (null != mEmptyView) {
            boolean isShowEmptyView = (null != mReqAdapter ? mReqAdapter.getItemCount() : 0) == 0;
            //setAdapter 时隐藏
            if (isShowEmptyView&&hasShowEmptyView) {
                mEmptyView.setVisibility(VISIBLE);
                return;
            } else if(isShowEmptyView) {
                hasShowEmptyView = true;
                mEmptyView.setVisibility(GONE);
            }
        }
    }


    /**
     * Called when an item view is detached from this RecyclerView.
     * <p/>
     * <p>Subclasses of RecyclerView may want to perform extra bookkeeping or modifications
     * of child views as they become detached. This will be called as a
     * {@link LayoutManager} fully detaches the child view from the parent and its window.</p>
     *
     * @param child Child view that is now detached from this RecyclerView and its associated window
     */
    @Override
    public void onChildDetachedFromWindow(View child) {
        super.onChildDetachedFromWindow(child);
    }

    /**
     * Called when an item view is attached to this RecyclerView.
     * <p/>
     * <p>Subclasses of RecyclerView may want to perform extra bookkeeping or modifications
     * of child views as they become attached. This will be called before a
     * {@link LayoutManager} measures or lays out the view and is a good time to perform these
     * changes.</p>
     *
     * @param child Child view that is now attached to this RecyclerView and its associated window
     */
    @Override
    public void onChildAttachedToWindow(View child) {
        super.onChildAttachedToWindow(child);
        if (null != mEmptyView && child.equals(mEmptyView))
            getEmptyViewHeight();
    }


    public View getEmptyView() {
        if (null != mEmptyViewListener && null != mEmptyView)
            mEmptyView.setOnClickListener(mEmptyViewListener);
        return mEmptyView;
    }

    public boolean showEmptyView(){
        final View view = getEmptyView();
        if (view != null){
            view.setVisibility(VISIBLE);
            return true;
        }
        return false;
    }

    /**
     * getEmptyView时 bindClickListener
     *
     * @param listener
     */
    public void setEmptyClickListener(OnClickListener listener) {
        this.mEmptyViewListener = listener;
    }

    public boolean isShowEmptyView() {
        return mEmptyView == null ? false : mEmptyView.isShown();
    }


    public void setDivider(Drawable divider) {
        if (!isDefaultItemDecoration || (mVerticalDividerHeight <= 0 && mHorizontalDividerHeight <= 0))
            return;

        if (this.mVerticalDivider != divider) {
            this.mVerticalDivider = divider;
        }

        if (this.mHorizontalDivider != divider) {
            this.mHorizontalDivider = divider;
        }

        if (null == mFamiliarDefaultItemDecoration) {
            addDefaultItemDecoration();
        } else {
            mFamiliarDefaultItemDecoration.setVerticalDividerDrawable(mVerticalDivider);
            mFamiliarDefaultItemDecoration.setHorizontalDividerDrawable(mHorizontalDivider);

            if (null != mWrapCoreRecyclerViewAdapter) {
                mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setDivider(Drawable dividerVertical, Drawable dividerHorizontal) {
        if (!isDefaultItemDecoration || (mVerticalDividerHeight <= 0 && mHorizontalDividerHeight <= 0))
            return;

        if (this.mVerticalDivider != dividerVertical) {
            this.mVerticalDivider = dividerVertical;
        }

        if (this.mHorizontalDivider != dividerHorizontal) {
            this.mHorizontalDivider = dividerHorizontal;
        }

        if (null == mFamiliarDefaultItemDecoration) {
            addDefaultItemDecoration();
        } else {
            mFamiliarDefaultItemDecoration.setVerticalDividerDrawable(mVerticalDivider);
            mFamiliarDefaultItemDecoration.setHorizontalDividerDrawable(mHorizontalDivider);

            if (null != mWrapCoreRecyclerViewAdapter) {
                mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setDividerVertical(Drawable dividerVertical) {
        if (!isDefaultItemDecoration || mVerticalDividerHeight <= 0) return;

        if (this.mVerticalDivider != dividerVertical) {
            this.mVerticalDivider = dividerVertical;
        }

        if (null == mFamiliarDefaultItemDecoration) {
            addDefaultItemDecoration();
        } else {
            mFamiliarDefaultItemDecoration.setVerticalDividerDrawable(mVerticalDivider);

            if (null != mWrapCoreRecyclerViewAdapter) {
                mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setDividerHorizontal(Drawable dividerHorizontal) {
        if (!isDefaultItemDecoration || mHorizontalDividerHeight <= 0) return;

        if (this.mHorizontalDivider != dividerHorizontal) {
            this.mHorizontalDivider = dividerHorizontal;
        }

        if (null == mFamiliarDefaultItemDecoration) {
            addDefaultItemDecoration();
        } else {
            mFamiliarDefaultItemDecoration.setHorizontalDividerDrawable(mHorizontalDivider);

            if (null != mWrapCoreRecyclerViewAdapter) {
                mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setDividerHeight(int height) {
        this.mVerticalDividerHeight = height;
        this.mHorizontalDividerHeight = height;

        if (isDefaultItemDecoration && null != mFamiliarDefaultItemDecoration) {
            mFamiliarDefaultItemDecoration.setVerticalDividerDrawableHeight(mVerticalDividerHeight);
            mFamiliarDefaultItemDecoration.setHorizontalDividerDrawableHeight(mHorizontalDividerHeight);

            if (null != mWrapCoreRecyclerViewAdapter) {
                mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setDividerVerticalHeight(int height) {
        this.mVerticalDividerHeight = height;

        if (isDefaultItemDecoration && null != mFamiliarDefaultItemDecoration) {
            mFamiliarDefaultItemDecoration.setVerticalDividerDrawableHeight(mVerticalDividerHeight);

            if (null != mWrapCoreRecyclerViewAdapter) {
                mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setDividerHorizontalHeight(int height) {
        this.mHorizontalDividerHeight = height;

        if (isDefaultItemDecoration && null != mFamiliarDefaultItemDecoration) {
            mFamiliarDefaultItemDecoration.setHorizontalDividerDrawableHeight(mHorizontalDividerHeight);

            if (null != mWrapCoreRecyclerViewAdapter) {
                mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * HeadView onBindViewHolder callback
     *
     * @param onHeadViewBindViewHolderListener OnHeadViewBindViewHolderListener
     */
    public void setOnHeadViewBindViewHolderListener(CoreRecyclerView.OnHeadViewBindViewHolderListener onHeadViewBindViewHolderListener) {
        if (null != mWrapCoreRecyclerViewAdapter) {
            mWrapCoreRecyclerViewAdapter.setOnHeadViewBindViewHolderListener(onHeadViewBindViewHolderListener);
        } else {
            this.mTempOnHeadViewBindViewHolderListener = onHeadViewBindViewHolderListener;
        }
    }

    /**
     * FooterView onBindViewHolder callback
     *
     * @param onFooterViewBindViewHolderListener OnFooterViewBindViewHolderListener
     */
    public void setOnFooterViewBindViewHolderListener(CoreRecyclerView.OnFooterViewBindViewHolderListener onFooterViewBindViewHolderListener) {
        if (null != mWrapCoreRecyclerViewAdapter) {
            mWrapCoreRecyclerViewAdapter.setOnFooterViewBindViewHolderListener(onFooterViewBindViewHolderListener);
        } else {
            this.mTempOnFooterViewBindViewHolderListener = onFooterViewBindViewHolderListener;
        }
    }

    /**
     * recycleview的复用机制
     * 多个headView 时请区分和重置
     * onHeadViewBindViewHolderListener
     * @param v
     */
    public void addHeaderView(View v) {
        addHeaderView(v, false);
    }

    public void addHeaderView(View v, boolean isScrollTo) {
        if (mHeaderView.contains(v)) return;

        mHeaderView.add(v);
        if (null != mWrapCoreRecyclerViewAdapter) {
            int pos = mHeaderView.size() - 1;
            mWrapCoreRecyclerViewAdapter.notifyItemInserted(pos);

            if (isScrollTo) {
                scrollToPosition(pos);
            }
        }

    }

    public boolean removeHeaderView(View v) {
        if (!mHeaderView.contains(v)) return false;

        if (null != mWrapCoreRecyclerViewAdapter) {
            mWrapCoreRecyclerViewAdapter.notifyItemRemoved(mHeaderView.indexOf(v));
        }
        return mHeaderView.remove(v);
    }

    public void addFooterView(View v) {
        addFooterView(v, false);
    }

    public void addFooterView(View v, boolean isScrollTo) {
        if (mFooterView.contains(v)) return;

        mFooterView.add(v);
        if (null != mWrapCoreRecyclerViewAdapter) {
            int pos = (null == mReqAdapter ? 0 : mReqAdapter.getItemCount()) + getHeaderViewsCount() + mFooterView.size() - 1;
            mWrapCoreRecyclerViewAdapter.notifyItemInserted(pos);
            if (isScrollTo) {
                scrollToPosition(pos);
            }
        }
    }

    public boolean removeFooterView(View v) {
        if (null == v)
            return false;
        if (!mFooterView.contains(v)) return false;

        if (null != mWrapCoreRecyclerViewAdapter) {
            int pos = (null == mReqAdapter ? 0 : mReqAdapter.getItemCount()) + getHeaderViewsCount() + mFooterView.indexOf(v);
            mWrapCoreRecyclerViewAdapter.notifyItemRemoved(pos);
        }
        return mFooterView.remove(v);
    }

    public int getHeaderViewsCount() {
        return mHeaderView.size();
    }

    public int getFooterViewsCount() {
        return mFooterView.size();
    }

    public int getFirstVisiblePosition() {
        LayoutManager layoutManager = getLayoutManager();

        if (null == layoutManager) return 0;

        int ret = -1;

        switch (mLayoutManagerType) {
            case LAYOUT_MANAGER_TYPE_LINEAR:
                ret = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() - getHeaderViewsCount();
                break;
            case LAYOUT_MANAGER_TYPE_GRID:
                ret = ((GridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() - getHeaderViewsCount();
                break;
            case LAYOUT_MANAGER_TYPE_STAGGERED_GRID:
                StaggeredGridLayoutManager tempStaggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] firstVisibleItemPositions = new int[tempStaggeredGridLayoutManager.getSpanCount()];
                tempStaggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(firstVisibleItemPositions);
                ret = firstVisibleItemPositions[0] - getHeaderViewsCount();
                break;
        }

        return ret < 0 ? 0 : ret;
    }

    public int getLastVisiblePosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (null == layoutManager) return -1;

        int curItemCount = (null != mReqAdapter ? mReqAdapter.getItemCount() - 1 : 0);
        int ret = -1;

        switch (mLayoutManagerType) {
            case LAYOUT_MANAGER_TYPE_LINEAR:
                ret = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() - getHeaderViewsCount();
                if (ret > curItemCount) {
                    ret -= getFooterViewsCount();
                }
                break;
            case LAYOUT_MANAGER_TYPE_GRID:
                ret = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() - getHeaderViewsCount();
                if (ret > curItemCount) {
                    ret -= getFooterViewsCount();
                }
                break;
            case LAYOUT_MANAGER_TYPE_STAGGERED_GRID:
                StaggeredGridLayoutManager tempStaggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] lastVisibleItemPositions = new int[tempStaggeredGridLayoutManager.getSpanCount()];
                tempStaggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastVisibleItemPositions);
                if (lastVisibleItemPositions.length > 0) {
                    int maxPos = lastVisibleItemPositions[0];
                    for (int curPos : lastVisibleItemPositions) {
                        if (curPos > maxPos) maxPos = curPos;
                    }
                    ret = maxPos - getHeaderViewsCount();
                    if (ret > curItemCount) {
                        ret -= getFooterViewsCount();
                    }
                }
                break;
        }

        return ret < 0 ? (null != mReqAdapter ? mReqAdapter.getItemCount() - 1 : 0) : ret;
    }

    public void setHeaderDividersEnabled(boolean isHeaderDividersEnabled) {
        this.isHeaderDividersEnabled = isHeaderDividersEnabled;
        if (isDefaultItemDecoration && null != mFamiliarDefaultItemDecoration) {
            mFamiliarDefaultItemDecoration.setHeaderDividersEnabled(isHeaderDividersEnabled);

            if (null != mWrapCoreRecyclerViewAdapter) {
                mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setFooterDividersEnabled(boolean isFooterDividersEnabled) {
        this.isFooterDividersEnabled = isFooterDividersEnabled;
        if (isDefaultItemDecoration && null != mFamiliarDefaultItemDecoration) {
            mFamiliarDefaultItemDecoration.setFooterDividersEnabled(isFooterDividersEnabled);

            if (null != mWrapCoreRecyclerViewAdapter) {
                mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        if (null == mWrapCoreRecyclerViewAdapter) {
            mTempOnItemClickListener = listener;
        } else {
            mWrapCoreRecyclerViewAdapter.setOnItemClickListener(listener);
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        if (null == mWrapCoreRecyclerViewAdapter) {
            mTempOnItemLongClickListener = listener;
        } else {
            mWrapCoreRecyclerViewAdapter.setOnItemLongClickListener(listener);
        }
    }

    /**
     * 滚动到最底部
     */
    public void scrollToLastPostion() {
        int pos = (null == mReqAdapter ? 0 : mReqAdapter.getItemCount()) + getHeaderViewsCount() + mFooterView.size() - 1;
        scrollToPosition(pos);
    }

    public View getLaseFootView() {
        if (null != mFooterView && mFooterView.size() > 0)
            return mFooterView.get(mFooterView.size() - 1);
        return null;
    }

    public void setAllFootViews(int Visable) {
        for (View temp:mFooterView){
            temp.setVisibility(Visable);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CoreRecyclerView CoreRecyclerView, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(CoreRecyclerView CoreRecyclerView, View view, int position);
    }

    public interface OnHeadViewBindViewHolderListener {
        void onHeadViewBindViewHolder(ViewHolder holder, int position, boolean isInitializeInvoke);
    }

    public interface OnFooterViewBindViewHolderListener {
        void onFooterViewBindViewHolder(ViewHolder holder, int position, boolean isInitializeInvoke);
    }

    private AdapterDataObserver mReqAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapCoreRecyclerViewAdapter.notifyDataSetChanged();
            LogUtils.e("onChanged","---");
            processEmptyView();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapCoreRecyclerViewAdapter.notifyItemInserted(getHeaderViewsCount() + positionStart);
            LogUtils.e("onItemRangeInserted","---");
            processEmptyView();
        }


        public void onItemRangeRemoved(int positionStart, int itemCount) {
            LogUtils.e("onItemRangeRemoved","---");
            mWrapCoreRecyclerViewAdapter.notifyItemRemoved(getHeaderViewsCount() + positionStart);
            processEmptyView();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            LogUtils.e("onItemRangeChanged","---");
            mWrapCoreRecyclerViewAdapter.notifyItemRangeChanged(getHeaderViewsCount() + positionStart, itemCount);
            processEmptyView();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapCoreRecyclerViewAdapter.notifyItemRangeChanged(getHeaderViewsCount()+positionStart,itemCount,payload);
            processEmptyView();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            LogUtils.e("onItemRangeMoved","---");
            mWrapCoreRecyclerViewAdapter.notifyItemMoved(getHeaderViewsCount() + fromPosition, getHeaderViewsCount() + toPosition);
        }
    };

    public Adapter getReqAdapter() {
        return mReqAdapter;
    }

    public void setReqAdapter(Adapter reqAdapter) {
        mReqAdapter = reqAdapter;
    }

    public CoreWrapRecyclerViewAdapter getWrapCoreRecyclerViewAdapter() {
        return mWrapCoreRecyclerViewAdapter;
    }

    public void setWrapCoreRecyclerViewAdapter(CoreWrapRecyclerViewAdapter wrapCoreRecyclerViewAdapter) {
        mWrapCoreRecyclerViewAdapter = wrapCoreRecyclerViewAdapter;
    }

    /**
     * Set the over-scroll mode for this view. Valid over-scroll modes are
     * {@link #OVER_SCROLL_ALWAYS} (default), {@link #OVER_SCROLL_IF_CONTENT_SCROLLS}
     * (allow over-scrolling only if the view content is larger than the container),
     * or {@link #OVER_SCROLL_NEVER}.
     * <p/>
     * Setting the over-scroll mode of a view will have an effect only if the
     * view is capable of scrolling.
     *
     * @param overScrollMode The new over-scroll mode for this view.
     */
    @Override
    public void setOverScrollMode(int overScrollMode) {
        super.setOverScrollMode(overScrollMode);
    }

    /**
     * Scroll the view with standard behavior for scrolling beyond the normal
     * content boundaries. Views that call this method should override
     * {@link #onOverScrolled(int, int, boolean, boolean)} to respond to the
     * results of an over-scroll operation.
     * <p/>
     * Views can use this method to handle any touch or fling-based scrolling.
     *
     * @param deltaX         Change in X in pixels
     * @param deltaY         Change in Y in pixels
     * @param scrollX        Current X scroll value in pixels before applying deltaX
     * @param scrollY        Current Y scroll value in pixels before applying deltaY
     * @param scrollRangeX   Maximum content scroll range along the X axis
     * @param scrollRangeY   Maximum content scroll range along the Y axis
     * @param maxOverScrollX Number of pixels to overscroll by in either direction
     *                       along the X axis.
     * @param maxOverScrollY Number of pixels to overscroll by in either direction
     *                       along the Y axis.
     * @param isTouchEvent   true if this scroll operation is the result of a touch event.
     * @return true if scrolling was clamped to an over-scroll boundary along either
     * axis, false otherwise.
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    public boolean isScrollToBottom() {
        switch (mLayoutManagerType) {
            case 1:
                return ((LinearLayoutManager) getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL ? !ViewCompat.canScrollVertically(this, 1) : !ViewCompat.canScrollHorizontally(this, 1);
            case 2:
                return ((LinearLayoutManager) getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL ? !ViewCompat.canScrollVertically(this, 1) : !ViewCompat.canScrollHorizontally(this, 1);
            case 3:
                return ((StaggeredGridLayoutManager) getLayoutManager()).getOrientation() == LinearLayoutManager.VERTICAL ? !ViewCompat.canScrollVertically(this, 1) : !ViewCompat.canScrollHorizontally(this, 1);
        }
        return false;
    }

    public  interface InterfaceSetAdapter {
        void onSetAdapter();
    }

    public List<View> getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(List<View> mHeaderView) {
        this.mHeaderView = mHeaderView;
    }
}