package com.wj.dawsonwanandroid.utils;

import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wj.dawsonwanandroid.R;

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
}
