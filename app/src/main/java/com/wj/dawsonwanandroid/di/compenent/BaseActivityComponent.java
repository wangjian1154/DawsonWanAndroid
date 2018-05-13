package com.wj.dawsonwanandroid.di.compenent;

import android.app.Activity;
import android.content.Context;

import com.wj.dawsonwanandroid.di.module.BaseActivityModule;
import com.wj.dawsonwanandroid.di.scope.ContextLife;
import com.wj.dawsonwanandroid.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by lw on 2017/1/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = BaseActivityModule.class)
public interface BaseActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

}
