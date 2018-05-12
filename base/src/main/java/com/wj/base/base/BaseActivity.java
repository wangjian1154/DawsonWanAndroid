package com.wj.base.base;

import android.os.Bundle;

import javax.inject.Inject;

/**
 * Created by wj on 2018/5/10.
 */
public abstract class BaseActivity<T extends BaseContract.AbstractPresenter>
        extends SimpleActivity implements BaseContract.BaseView {

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }
}
