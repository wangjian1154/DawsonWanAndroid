package com.wj.dawsonwanandroid.di.module;

import android.app.Activity;
import android.content.Context;

import com.wj.dawsonwanandroid.di.scope.ContextLife;
import com.wj.dawsonwanandroid.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lw on 2017/1/19.
 */
@Module
public class BaseActivityModule {
    private Activity mActivity;

    public BaseActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
