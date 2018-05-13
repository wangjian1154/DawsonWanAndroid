package com.wj.dawsonwanandroid.di.compenent;

import android.content.Context;

import com.wj.dawsonwanandroid.di.module.ApplicationModule;
import com.wj.dawsonwanandroid.di.scope.ContextLife;
import com.wj.dawsonwanandroid.di.scope.PerApp;

import dagger.Component;


/**
 * Created by lw on 2017/1/19.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}