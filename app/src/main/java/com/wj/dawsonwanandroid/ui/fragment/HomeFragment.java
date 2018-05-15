package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wj.base.base.BaseContract;
import com.wj.base.base.BaseFragment;
import com.wj.base.utils.BannerImageLoader;
import com.wj.base.utils.BaseUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.HomeBanner;
import com.wj.dawsonwanandroid.ui.contract.HomeContract;
import com.wj.dawsonwanandroid.ui.presenter.HomePresenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<ArticleBean> articleList;

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        BaseUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getActivity()));
        mPresenter.loadData(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }


    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void setHomeBanner(List<HomeBanner> banners) {
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
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .start();
    }

    @Override
    public void setListData(BaseResponse<List<ArticleBean>> articleBean, boolean isRefresh) {
        List<ArticleBean> data = articleBean.getData();
        if (isRefresh) articleList.clear();
        if (data != null && data.size() > 0) {
            articleList.addAll(data);
        }

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
}
