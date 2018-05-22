package com.wj.dawsonwanandroid.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wj.base.base.BaseFragment;
import com.wj.base.base.BaseFragmentPageAdapter;
import com.wj.base.utils.ScreenUtils;
import com.wj.base.utils.StatusBarUtil;
import com.wj.base.utils.ToastUtils;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.ProjectCategoryBean;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.ui.adapter.MyCommonNavigatorAdapter;
import com.wj.dawsonwanandroid.ui.contract.OpenSourceContract;
import com.wj.dawsonwanandroid.ui.presenter.OpenSourcePresenter;
import com.wj.dawsonwanandroid.utils.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目
 */
public class OpenSourceFragment extends BaseFragment<OpenSourcePresenter> implements OpenSourceContract.View {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;

    private List<Fragment> fragments;
    private List<String> titles;
    private MyCommonNavigatorAdapter adapter;
    private BaseFragmentPageAdapter pageAdapter;
    private CommonNavigator commonNavigator;

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();

        magicIndicator.setBackgroundColor(Color.WHITE);
        commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.65f);

        adapter = new MyCommonNavigatorAdapter(titles, viewPager);
        pageAdapter = new BaseFragmentPageAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(pageAdapter);
        commonNavigator.setAdapter(adapter);

    }

    @Override
    protected int getLayoutId() {
        StatusBarUtil.setColor(getActivity(), Color.WHITE);
        return R.layout.fragment_open_source;
    }

    @Override
    protected void lazyLoad() {
        mPresenter.loadCategory();
        super.lazyLoad();

    }

    @Override
    public OpenSourcePresenter createPresenter() {
        return new OpenSourcePresenter();
    }

    @Override
    public void setCategoryData(BaseResponse<List<ProjectCategoryBean>> list) {
        titles.clear();
        if (list != null && list.getData() != null && list.getData().size() > 0) {
            List<ProjectCategoryBean> listData = list.getData();
            for (ProjectCategoryBean listDatum : listData) {
                titles.add(listDatum.getName());
                fragments.add(ProjectFragment.newInstance(listDatum.getId()));
            }
        }
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        adapter.notifyDataSetChanged();
        pageAdapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(fragments.size());
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }

}
