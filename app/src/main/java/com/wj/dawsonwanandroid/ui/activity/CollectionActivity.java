package com.wj.dawsonwanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wj.base.base.BaseActivity;
import com.wj.base.utils.BaseUtils;
import com.wj.base.utils.ScreenUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.base.view.TitleBar;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.core.JumpModel;
import com.wj.dawsonwanandroid.core.MyApp;
import com.wj.dawsonwanandroid.ui.adapter.ArticleListAdapter;
import com.wj.dawsonwanandroid.ui.contract.CollectionContact;
import com.wj.dawsonwanandroid.ui.presenter.CollectionPresenter;
import com.wj.dawsonwanandroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * 我的收藏
 */
public class CollectionActivity extends BaseActivity<CollectionPresenter> implements
        CollectionContact.View, OnRefreshLoadmoreListener, BaseQuickAdapter.OnItemClickListener,
        View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.iv_to_top)
    ImageView ivToTop;
    @BindView(R.id.title_bar)
    TitleBar titleBar;

    private List<ArticleBean> articleList;
    private ArticleListAdapter adapter;
    private int height = ScreenUtils.getHeightInPx(MyApp.getInstance());
    private int overallXScroll = 0;


    public static void show(Context context) {
        Intent intent = new Intent(context, CollectionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        titleBar.setTitle("我的收藏");
        articleList = new ArrayList<>();
        setProgressIndicator(true);

        adapter = new ArticleListAdapter(articleList);
        BaseUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());

        smartRefresh.setOnRefreshLoadmoreListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        titleBar.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                overallXScroll = overallXScroll + dy;
                if (overallXScroll <= 0) {
                    ivToTop.setVisibility(View.GONE);
                } else if (overallXScroll > 0 && overallXScroll <= height) {
                    ivToTop.setVisibility(View.GONE);
                } else {
                    ivToTop.setVisibility(View.VISIBLE);

                }
            }
        });


        mPresenter.loadData(true);
    }


    @Override
    public CollectionPresenter createPresenter() {
        return new CollectionPresenter();
    }

    @Override
    public void setListData(BaseResponse<ArticleListBean> articleBean, boolean isRefresh) {
        ArticleListBean data = articleBean.getData();
        if (isRefresh) articleList.clear();
        if (data != null) {
            List<ArticleBean> mData = data.datas;
            if (mData != null && mData.size() > 0) {
                articleList.addAll(mData);
            }
        }
        adapter.notifyDataSetChanged();
        Utils.refreshComplete(smartRefresh);
        setProgressIndicator(false);
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.loadData(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.loadData(true);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        JumpModel.getInstance().jumpArticleDetailActivity(this, articleList.get(position));
    }

    @OnClick({R.id.iv_to_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_to_top:
                recyclerView.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    public void collectionArticle(BaseResponse result, int position) {
        if (result != null) {
            if (result.getErrorCode() == Constants.RESPONSE.SUCCESS) {
                //收藏成功
                articleList.get(position).collect = true;
//                adapter.notifyItemChanged(position, 1);
                adapter.notifyDataSetChanged();
                setProgressIndicator(false);
                Log.i("www", "点击的position" + position);
            } else {
                ToastUtils.showShort(result.getErrorMsg());
            }
        }
    }

    @Override
    public void unCollectionArticle(BaseResponse result, int position) {
        if (result != null) {
            if (result.getErrorCode() == Constants.RESPONSE.SUCCESS) {
                //取消收藏成功
                articleList.get(position).collect = false;
                adapter.notifyDataSetChanged();
                setProgressIndicator(false);
            } else {
                ToastUtils.showShort(result.getErrorMsg());
            }
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (MyApp.checkLogin(this)) {
            setProgressIndicator(true);
            ArticleBean item = articleList.get(position);
            int articleId = item.id;
            if (item.collect) {
                mPresenter.unCollection(articleId, position);
            } else {
                mPresenter.collection(articleId, position);
            }
        }
    }

    @Override
    public void onEventMainThread(Message msg) {
        super.onEventMainThread(msg);
        switch (msg.what) {
            case Constants.Key_EventBus_Msg.LOGIN_SUCCESS:
            case Constants.Key_EventBus_Msg.EXIT_LOGIN:
                mPresenter.loadData(true);
                break;
        }
    }
}
