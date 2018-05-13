package com.wj.dawsonwanandroid.di.compenent;

import android.app.Activity;
import android.content.Context;

import com.wj.dawsonwanandroid.di.module.BaseFragmentModule;
import com.wj.dawsonwanandroid.di.scope.ContextLife;
import com.wj.dawsonwanandroid.di.scope.PerFragment;
import com.wj.dawsonwanandroid.ui.fragment.CategoryFragment;
import com.wj.dawsonwanandroid.ui.fragment.HomeFragment;
import com.wj.dawsonwanandroid.ui.fragment.MineFragment;

import dagger.Component;

/**
 * Created by lw on 2017/1/19.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = BaseFragmentModule.class)
public interface BaseFragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(HomeFragment fragment);

    void inject(CategoryFragment fragment);

    void inject(MineFragment fragment);

}
