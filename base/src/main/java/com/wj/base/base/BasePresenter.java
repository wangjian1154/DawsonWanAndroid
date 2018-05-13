package com.wj.base.base;

/**
 * Created by wj on 2018/5/10.
 */
public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.AbstractPresenter<T> {

    protected T mView;
    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
