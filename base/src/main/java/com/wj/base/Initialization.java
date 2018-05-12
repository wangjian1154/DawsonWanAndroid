package com.wj.base;

import android.content.Context;

/**
 * 工具类初始化
 */

public class Initialization {

    public static Context context;

    public static final boolean DEBUG = true;

    /**
     * 初始化
     *
     * @param context
     */
    public Initialization(Context context) {
        this.context = context;
    }


    public static Context getContext() {
        return context;
    }

}
