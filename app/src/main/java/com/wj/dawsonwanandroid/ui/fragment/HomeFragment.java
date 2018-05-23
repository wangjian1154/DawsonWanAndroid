package com.wj.dawsonwanandroid.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wj.base.base.BaseFragment;
import com.wj.base.utils.BannerImageLoader;
import com.wj.base.utils.BaseUtils;
import com.wj.base.utils.ScreenUtils;
import com.wj.base.utils.StatusBarUtil;
import com.wj.base.utils.ToastUtils;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.HomeBanner;
import com.wj.dawsonwanandroid.core.JumpModel;
import com.wj.dawsonwanandroid.core.MyApp;
import com.wj.dawsonwanandroid.ui.activity.WebViewActivity;
import com.wj.dawsonwanandroid.ui.adapter.ArticleListAdapter;
import com.wj.dawsonwanandroid.ui.contract.HomeContract;
import com.wj.dawsonwanandroid.ui.presenter.HomePresenter;
import com.wj.dawsonwanandroid.utils.Utils;
import com.wj.dawsonwanandroid.view.dialog.ArticleCollectionDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View,
        OnRefreshLoadmoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {

    private Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_to_top)
    ImageView ivToTop;

    private List<ArticleBean.DatasBean> articleList;
    private ArticleListAdapter adapter;
    private int height = ScreenUtils.getHeightInPx(MyApp.getInstance());
    private int overallXScroll = 0;

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        articleList = new ArrayList<>();
        BaseUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getActivity()));
        adapter = new ArticleListAdapter(articleList);
        recyclerView.setAdapter(adapter);

        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_head, null);
        banner = (Banner) headView.findViewById(R.id.banner);
        banner = headView.findViewById(R.id.banner);
        adapter.addHeaderView(headView);

        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);

        setProgressIndicator(true);

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mPresenter.loadData(true);
    }


    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void setHomeBanner(final List<HomeBanner> banners) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (HomeBanner bean : banners) {
            images.add(bean.getImagePath());
            titles.add(bean.getTitle());
        }
        banner.setImages(images)
                .setImageLoader(new BannerImageLoader())
                .setDelayTime(5 * 1000)
                .setBannerTitles(titles)
                .setIndicatorGravity(BannerConfig.LEFT)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        JumpModel.getInstance().jumpWebActivity(getActivity(), banners.get(position).getUrl());
                    }
                })
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .start();
        Utils.refreshComplete(smartRefreshLayout);
        setProgressIndicator(false);
    }

    @Override
    public void setListData(BaseResponse<ArticleBean> articleBean, boolean isRefresh) {
        ArticleBean data = articleBean.getData();
        if (isRefresh) articleList.clear();
        if (data != null) {
            List<ArticleBean.DatasBean> mData = data.datas;
            if (mData != null && mData.size() > 0) {
                articleList.addAll(mData);
            }
        }
        adapter.notifyDataSetChanged();
        Utils.refreshComplete(smartRefreshLayout);
        setProgressIndicator(false);
    }

    @Override
    public void showFailed(String error) {
        ToastUtils.showShort(error);
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
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
        JumpModel.getInstance().jumpWebActivity(getActivity(), articleList.get(position).link);
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
//        ArticleCollectionDialog dialog = new ArticleCollectionDialog();
//        dialog.show(getFragmentManager(), ArticleCollectionDialog.class.getSimpleName());
        return false;
    }

    @OnClick({R.id.iv_to_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_to_top:
                recyclerView.smoothScrollToPosition(0);
                break;
        }
    }
}
