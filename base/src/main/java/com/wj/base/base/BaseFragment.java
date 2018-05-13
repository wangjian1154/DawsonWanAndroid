package com.wj.base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wj.dawsonwanandroid.di.compenent.BaseFragmentComponent;
import com.wj.dawsonwanandroid.di.module.BaseFragmentModule;

import javax.inject.Inject;

/**
 * Created by wj on 2018/5/10.
 */
public abstract class BaseFragment<T extends BaseContract.AbstractPresenter>
        extends SimpleFragment implements BaseContract.BaseView {

    @Inject
    protected T mPresenter;

    protected BaseFragmentComponent mFragmentComponent;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
