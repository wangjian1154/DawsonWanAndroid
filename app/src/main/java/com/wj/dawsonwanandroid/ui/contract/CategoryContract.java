package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;

/**
 * Created by wj on 2018/5/17.
 */
public interface CategoryContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter extends BaseContract.AbstractPresenter<HomeContract.View> {

    }
}
