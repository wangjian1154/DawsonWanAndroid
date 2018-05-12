package com.wj.base.base;

public interface BaseContract {
 
    interface AbstractPresenter<T extends BaseContract.BaseView> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void showLoading();

        void hideLoading();

        void showError(String msg);

    }
}
