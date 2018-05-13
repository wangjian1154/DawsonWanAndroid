package com.wj.base.base;

import io.reactivex.ObservableTransformer;

public interface BaseContract {
 
    interface AbstractPresenter<T extends BaseContract.BaseView> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void showLoading();

        void hideLoading();

        void showError(String msg);

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> ObservableTransformer bindToLife();

    }
}
