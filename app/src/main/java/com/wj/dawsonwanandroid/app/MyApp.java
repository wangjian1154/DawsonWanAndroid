package com.wj.dawsonwanandroid.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by wj on 2018/5/11.
 */
public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }
}
