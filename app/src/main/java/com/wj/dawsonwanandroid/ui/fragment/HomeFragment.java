package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wj.base.base.BaseFragment;
import com.wj.base.base.SimpleFragment;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.ui.contract.HomeContract;
import com.wj.dawsonwanandroid.ui.presenter.HomePresenter;
import com.youth.banner.Banner;

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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void setHomeBanner(List<Banner> banner) {

    }

    @Override
    public void setListData() {

    }
}
