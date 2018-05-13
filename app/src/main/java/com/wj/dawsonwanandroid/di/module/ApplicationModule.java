package com.wj.dawsonwanandroid.di.module;

import android.content.Context;

import com.wj.dawsonwanandroid.core.MyApp;
import com.wj.dawsonwanandroid.di.scope.ContextLife;
import com.wj.dawsonwanandroid.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;


/**
 * Created by lw on 2017/1/19.
 */
@Module
public class ApplicationModule {
    private MyApp mApplication;

    public ApplicationModule(MyApp application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
