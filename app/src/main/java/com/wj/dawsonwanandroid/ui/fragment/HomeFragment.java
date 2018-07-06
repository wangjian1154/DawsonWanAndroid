package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wj.base.base.BaseFragment;
import com.wj.base.utils.BannerImageLoader;
import com.wj.base.utils.BaseUtils;
import com.wj.base.utils.ScreenUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.HomeBanner;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.core.JumpModel;
import com.wj.dawsonwanandroid.core.MyApp;
import com.wj.dawsonwanandroid.ui.adapter.ArticleListAdapter;
import com.wj.dawsonwanandroid.ui.contract.HomeContract;
import com.wj.dawsonwanandroid.ui.presenter.HomePresenter;
import com.wj.dawsonwanandroid.utils.Utils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View,
        OnRefreshLoadmoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_to_top)
    ImageView ivToTop;

    private List<ArticleBean> articleList;
    private ArticleListAdapter adapter;
    private int height = ScreenUtils.getHeightInPx(MyApp.getInstance());
    private int overallXScroll = 0;

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        articleList = new ArrayList<>();
        BaseUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getActivity()));
        adapter = new ArticleListAdapter(articleList,true);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());

        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_home_head, null);
        banner = headView.findViewById(R.id.banner);
        adapter.addHeaderView(headView);

        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);

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
                        JumpModel.getInstance().jumpWebView(getActivity(), banners.get(position).getUrl());
                    }
                })
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .start();
        Utils.refreshComplete(smartRefreshLayout);
        setProgressIndicator(false);
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
        Utils.refreshComplete(smartRefreshLayout);
        setProgressIndicator(false);
    }

    @Override
    public void showFailed(String error) {
        ToastUtils.showShort(error);
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
        JumpModel.getInstance().jumpArticleDetailActivity(getActivity(), articleList.get(position));
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (MyApp.checkLogin(getActivity())) {
            setProgressIndicator(true);
            ArticleBean item = articleList.get(position);
            int articleId = item.id;
            if (item.collect) {
                mPresenter.unCollection(articleId, position);
            }else{
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
