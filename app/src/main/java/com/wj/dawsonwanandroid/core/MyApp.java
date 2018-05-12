package com.wj.dawsonwanandroid.core;

import android.app.Application;
import android.content.Context;

import com.wj.base.Initialization;

/**
 * Created by wj on 2018/5/11.
 */
public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        new Initialization(this);
    }
}
