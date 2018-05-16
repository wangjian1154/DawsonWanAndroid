package com.wj.dawsonwanandroid.utils;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by wj on 2018/5/16.
 */
public class Utils {

    /**
     * 刷新完成
     * @param smartRefreshLayout
     */
    public static void refreshComplete(SmartRefreshLayout smartRefreshLayout) {
        smartRefreshLayout.finishLoadmore();
        smartRefreshLayout.finishRefresh();
    }
}
