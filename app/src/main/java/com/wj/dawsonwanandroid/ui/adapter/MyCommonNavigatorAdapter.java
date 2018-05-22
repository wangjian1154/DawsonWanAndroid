package com.wj.dawsonwanandroid.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.wj.dawsonwanandroid.utils.Utils;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import java.util.List;

/**
 * Created by wj on 2018/5/22.
 */
public class MyCommonNavigatorAdapter extends CommonNavigatorAdapter {


    private List<String> titles;
    private ViewPager viewPager;

    public MyCommonNavigatorAdapter(List<String> titles, ViewPager viewPager) {
        this.titles = titles;
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        return Utils.initColorTransitionPagerTitleView(context, index, titles.get(index), viewPager);
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return Utils.initLinePagerIndicator(context);
    }
}
