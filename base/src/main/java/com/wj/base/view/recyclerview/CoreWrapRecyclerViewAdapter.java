package com.wj.base.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;


import com.chad.library.adapter.base.BaseViewHolder;
import com.just.agentweb.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Wrap CoreRecyclerView Adapter
 * Created by iWgang on 15/10/31.
 * https://github.com/iwgang/CoreRecyclerView
 */
public class CoreWrapRecyclerViewAdapter extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {
    private static final int MIN_INTERVAL_CLICK_TIME = 100;

    private static final int VIEW_TYPE_HEADER = -1;
    private static final int VIEW_TYPE_FOOTER = -2;
    private static final int VIEW_TYPE_EMPTY_VIEW = -3;

    private List<View> mHeaderView;
    private List<View> mFooterView;
    private RecyclerView.Adapter mReqAdapter;
    private int curHeaderPos;
    private int curFooterPos;
    private int mLayoutManagerType = CoreRecyclerView.LAYOUT_MANAGER_TYPE_LINEAR;
    private CoreRecyclerView.OnItemClickListener mOnItemClickListener;
    private CoreRecyclerView.OnItemLongClickListener mOnItemLongClickListener;
    private CoreRecyclerView mCoreRecyclerView;
    private long mLastClickTime;
    private CoreRecyclerView.OnHeadViewBindViewHolderListener mOnHeadViewBindViewHolderListener;
    private CoreRecyclerView.OnFooterViewBindViewHolderListener mOnFooterViewBindViewHolderListener;
    private List<Integer> mHeadOrFooterInitInvokeViewBindViewFlag = new ArrayList<>();


    public CoreWrapRecyclerViewAdapter(CoreRecyclerView CoreRecyclerView, RecyclerView.Adapter reqAdapter, List<View> mHeaderView, List<View> mFooterView, int layoutManagerType) {
        this.mCoreRecyclerView = CoreRecyclerView;
        this.mReqAdapter = reqAdapter;
        this.mHeaderView = mHeaderView;
        this.mFooterView = mFooterView;
        this.mLayoutManagerType = layoutManagerType;
    }

    public int getHeadersCount() {
        return null != mHeaderView ? mHeaderView.size() : 0;
    }

    public int getFootersCount() {
        return null != mFooterView ? mFooterView.size() : 0;
    }

    private int getReqAdapterCount() {
        return null != mReqAdapter ? mReqAdapter.getItemCount() : 0;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        int tempItemCount = mReqAdapter.getItemCount();
        count += tempItemCount == 0 ? 1 : tempItemCount;
        if (null != mHeaderView && mHeaderView.size() > 0) {
            count += mHeaderView.size();
        }

        if (null != mFooterView && mFooterView.size() > 0) {
            count += mFooterView.size();
        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        // header view
        if (isHeaderView(position)) {
            this.curHeaderPos = position;
            Log.e("-----mHeaderView:",mHeaderView.get(position).getId() + "position:" + position);
            return VIEW_TYPE_HEADER;
        }

        int headersCount = getHeadersCount();
        // item view
        int adapterCount = 0;
        if (getReqAdapterCount() > 0 && position >= headersCount) {
            int adjPosition = position - headersCount;
            adapterCount = mReqAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mReqAdapter.getItemViewType(adjPosition);
            }
        } else {
            // empty view
            if (position == headersCount) return VIEW_TYPE_EMPTY_VIEW;
        }

        // footer view
        this.curFooterPos = position - headersCount - adapterCount;
        return VIEW_TYPE_FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER: {
                // create header view
                EmptyHeaderOrFooterViewHolder headerViewHolder;
                View tempHeadView = mHeaderView.get(curHeaderPos);
                if (mLayoutManagerType == CoreRecyclerView.LAYOUT_MANAGER_TYPE_STAGGERED_GRID) {
                    FrameLayout mContainerView = new FrameLayout(tempHeadView.getContext());
                    mContainerView.addView(tempHeadView);
                    headerViewHolder = new EmptyHeaderOrFooterViewHolder(mContainerView);
                    StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setFullSpan(true);
                    headerViewHolder.itemView.setLayoutParams(layoutParams);
                } else {
                    headerViewHolder = new EmptyHeaderOrFooterViewHolder(tempHeadView);
                }

                // fix multiple headerView disorder
                if (mHeaderView.size() > 2) {
                    headerViewHolder.setIsRecyclable(false);
                }
                return headerViewHolder;
            }
            case VIEW_TYPE_FOOTER: {
                // create footer view
                LogUtils.e("WRAP_ADAPTER:", "VIEW_TYPE_FOOTER");
                EmptyHeaderOrFooterViewHolder footerViewHolder;
                View tempFooterView;

                // fix fast delete IndexOutOfBoundsException
                int footerViewCount = mFooterView.size();
                if (curFooterPos >= footerViewCount) {
                    curFooterPos = footerViewCount - 1;
                }
                tempFooterView = mFooterView.get(curFooterPos);
                ViewParent tempFooterViewParent = tempFooterView.getParent();
                if (null != tempFooterViewParent) {
                    ((ViewGroup) tempFooterViewParent).removeView(tempFooterView);
                }
                if (mLayoutManagerType == CoreRecyclerView.LAYOUT_MANAGER_TYPE_STAGGERED_GRID) {
                    FrameLayout mContainerView = new FrameLayout(tempFooterView.getContext());
                    mContainerView.addView(tempFooterView);
                    footerViewHolder = new EmptyHeaderOrFooterViewHolder(mContainerView);
                    StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setFullSpan(true);
                    footerViewHolder.itemView.setLayoutParams(layoutParams);
                } else {
                    footerViewHolder = new EmptyHeaderOrFooterViewHolder(tempFooterView);
                }

                // fix multiple footerView disorder
                if (mFooterView.size() > 2) {
                    footerViewHolder.setIsRecyclable(false);
                }
                return footerViewHolder;
            }
            case VIEW_TYPE_EMPTY_VIEW: {
                EmptyHeaderOrFooterViewHolder emptyViewHolder;
                View emptyView = mCoreRecyclerView.getEmptyView();
                if (mLayoutManagerType == CoreRecyclerView.LAYOUT_MANAGER_TYPE_STAGGERED_GRID) {
                    FrameLayout mContainerView = new FrameLayout(emptyView.getContext());
                    mContainerView.addView(emptyView);
                    emptyViewHolder = new EmptyHeaderOrFooterViewHolder(mContainerView);
                    StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setFullSpan(true);
                    emptyViewHolder.itemView.setLayoutParams(layoutParams);
                } else {
                    emptyViewHolder = new EmptyHeaderOrFooterViewHolder(emptyView);
                }

                return emptyViewHolder;
            }
            default:
                // create default item view
                RecyclerView.ViewHolder itemViewHolder = mReqAdapter.onCreateViewHolder(parent, viewType);
                // add click listener
                if (null != mOnItemClickListener) {
                    itemViewHolder.itemView.setOnClickListener(this);
                }
                if (null != mOnItemLongClickListener) {
                    itemViewHolder.itemView.setOnLongClickListener(this);
                }
                return itemViewHolder;
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        int position = holder.getAdapterPosition();
        if (null == mReqAdapter || isHeaderView(position) || isFooterView(position)) {
            return ;
        }

        mReqAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        int position = holder.getAdapterPosition();
        if (null == mReqAdapter || isHeaderView(position) || isFooterView(position)) {
            return ;
        }

        mReqAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        if (null == mReqAdapter) {
            return ;
        }

        mReqAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        if (null == mReqAdapter) {
            return ;
        }

        mReqAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

        int position = holder.getAdapterPosition();
        if (null == mReqAdapter || isHeaderView(position) || isFooterView(position)) {
            return ;
        }

        mReqAdapter.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            localRefresh(holder,position,payloads);
        }
    }

    public void localRefresh(RecyclerView.ViewHolder holder, int position, List payloads){
        int curItemViewType = getItemViewType(position);

        if (curItemViewType == VIEW_TYPE_HEADER && null != mOnHeadViewBindViewHolderListener) {
            // head view
            boolean isInitializeInvoke = false;
            int tempHeadViewHashCode = holder.itemView.hashCode();
            if (!mHeadOrFooterInitInvokeViewBindViewFlag.contains(tempHeadViewHashCode)) {
                mHeadOrFooterInitInvokeViewBindViewFlag.add(tempHeadViewHashCode);
                isInitializeInvoke = true;
            }
            mOnHeadViewBindViewHolderListener.onHeadViewBindViewHolder(holder, position, isInitializeInvoke);
        } else if (curItemViewType == VIEW_TYPE_FOOTER && null != mOnFooterViewBindViewHolderListener) {
            LogUtils.e("onBindViewHolder:", "VIEW_TYPE_FOOTER");
            // footer view
            boolean isInitializeInvoke = false;
            int tempFooterViewHashCode = holder.itemView.hashCode();
            if (!mHeadOrFooterInitInvokeViewBindViewFlag.contains(tempFooterViewHashCode)) {
                mHeadOrFooterInitInvokeViewBindViewFlag.add(tempFooterViewHashCode);
                isInitializeInvoke = true;
            }
            mOnFooterViewBindViewHolderListener.onFooterViewBindViewHolder(holder, position - getHeadersCount() - (null != mReqAdapter ? mReqAdapter.getItemCount() : 0), isInitializeInvoke);
        } else if (curItemViewType >= 0) {
            final int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (mReqAdapter != null) {
                adapterCount = mReqAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mReqAdapter.onBindViewHolder(holder, adjPosition,payloads);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int curItemViewType = getItemViewType(position);

        if (curItemViewType == VIEW_TYPE_HEADER && null != mOnHeadViewBindViewHolderListener) {
            // head view
            boolean isInitializeInvoke = false;
            int tempHeadViewHashCode = holder.itemView.hashCode();
            if (!mHeadOrFooterInitInvokeViewBindViewFlag.contains(tempHeadViewHashCode)) {
                mHeadOrFooterInitInvokeViewBindViewFlag.add(tempHeadViewHashCode);
                isInitializeInvoke = true;
            }
            mOnHeadViewBindViewHolderListener.onHeadViewBindViewHolder(holder, position, isInitializeInvoke);
        } else if (curItemViewType == VIEW_TYPE_FOOTER && null != mOnFooterViewBindViewHolderListener) {
            LogUtils.e("onBindViewHolder:", "VIEW_TYPE_FOOTER");
            // footer view
            boolean isInitializeInvoke = false;
            int tempFooterViewHashCode = holder.itemView.hashCode();
            if (!mHeadOrFooterInitInvokeViewBindViewFlag.contains(tempFooterViewHashCode)) {
                mHeadOrFooterInitInvokeViewBindViewFlag.add(tempFooterViewHashCode);
                isInitializeInvoke = true;
            }
            mOnFooterViewBindViewHolderListener.onFooterViewBindViewHolder(holder, position - getHeadersCount() - (null != mReqAdapter ? mReqAdapter.getItemCount() : 0), isInitializeInvoke);
        } else if (curItemViewType >= 0) {
            // item view
            final int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (mReqAdapter != null) {
                adapterCount = mReqAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mReqAdapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }
    }

    public void setOnItemClickListener(CoreRecyclerView.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(CoreRecyclerView.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        long curTime = System.currentTimeMillis();
        if (null != mOnItemClickListener && curTime - mLastClickTime > MIN_INTERVAL_CLICK_TIME) {
            mLastClickTime = curTime;
            mOnItemClickListener.onItemClick(mCoreRecyclerView, v, mCoreRecyclerView.getChildAdapterPosition(v) - mCoreRecyclerView.getHeaderViewsCount());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        long curTime = System.currentTimeMillis();
        if (null != mOnItemClickListener && curTime - mLastClickTime > MIN_INTERVAL_CLICK_TIME) {
            mLastClickTime = curTime;
            return mOnItemLongClickListener.onItemLongClick(mCoreRecyclerView, v, mCoreRecyclerView.getChildAdapterPosition(v) - mCoreRecyclerView.getHeaderViewsCount());
        }
        return false;
    }

    public void setOnHeadViewBindViewHolderListener(CoreRecyclerView.OnHeadViewBindViewHolderListener mOnHeadViewBindViewHolderListener) {
        this.mOnHeadViewBindViewHolderListener = mOnHeadViewBindViewHolderListener;
    }

    public void setOnFooterViewBindViewHolderListener(CoreRecyclerView.OnFooterViewBindViewHolderListener mOnFooterViewBindViewHolderListener) {
        this.mOnFooterViewBindViewHolderListener = mOnFooterViewBindViewHolderListener;
    }

    private boolean isHeaderView(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterView(int position) {
        return getFootersCount() > 0 && position - getHeadersCount() - getReqAdapterCount() >= 0;
    }

    class EmptyHeaderOrFooterViewHolder extends BaseViewHolder {
        public EmptyHeaderOrFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
