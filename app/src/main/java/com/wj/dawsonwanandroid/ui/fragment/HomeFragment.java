package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.wj.base.base.BaseFragment;
import com.wj.dawsonwanandroid.base.BaseFragment;
import com.wj.base.utils.BannerImageLoader;
import com.wj.base.utils.ToastUtils;
import com.wj.dawsonwanandroid.R;
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

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
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
    public void showLoading() {
        setProgressIndicator(true);
    }

    @Override
    public void hideLoading() {
        setProgressIndicator(false);
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void setHomeBanner(List<HomeBanner> banners) {
        List<String> images = new ArrayList<>();
        for (HomeBanner bean : banners) {
            images.add(bean.getImagePath());
        }
        banner.setImages(images)
                .setImageLoader(new BannerImageLoader())
                .setDelayTime(5 * 1000)
                .setIndicatorGravity(BannerConfig.LEFT)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
    }

    @Override
    public void setListData() {

    }

    @Override
    public void showFailed(String error) {
        ToastUtils.showShort(error);
    }
}
