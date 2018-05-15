package com.wj.base.base;

import android.os.Bundle;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by wj on 2018/5/10.
 */
public abstract class BaseActivity<T extends BaseContract.AbstractPresenter>
        extends SimpleActivity implements BaseContract.BaseView {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onViewCreated();
    }

    public abstract T createPresenter();

    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }
}
