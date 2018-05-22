package com.wj.dawsonwanandroid.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wj.base.utils.ScreenUtils;
import com.wj.dawsonwanandroid.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * Created by wj on 2018/5/16.
 */
public class Utils {

    /**
     * 刷新完成
     *
     * @param smartRefreshLayout
     */
    public static void refreshComplete(SmartRefreshLayout smartRefreshLayout) {
        smartRefreshLayout.finishLoadmore();
        smartRefreshLayout.finishRefresh();
    }

    public static String getResourcesString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static int getResourcesColor(Context context, int id) {
        return context.getResources().getColor(id);
    }

    public static LinePagerIndicator initLinePagerIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineHeight(ScreenUtils.dp2px(6));
        indicator.setLineWidth(ScreenUtils.dp2px(10));
        indicator.setRoundRadius(ScreenUtils.dp2px(3));
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        indicator.setColors(Color.parseColor("#00c853"));
        return indicator;
    }

    public static IPagerTitleView initColorTransitionPagerTitleView(
            Context context, final int index, String title, final ViewPager viewPager) {
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
        colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
        colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
        colorTransitionPagerTitleView.setText(title);
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(index);
            }
        });

        return colorTransitionPagerTitleView;
    }
}
