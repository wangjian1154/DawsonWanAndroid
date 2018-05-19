package com.wj.dawsonwanandroid.ui.contract;

import com.google.gson.JsonObject;
import com.wj.base.base.BaseContract;

public interface LoginContract {

    interface View extends BaseContract.BaseView {

        void onLoginCallback(JsonObject result);

        void onRegisterCallback(JsonObject result);
    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {

        void login(String username, String password);

        void register(String username, String password);
    }
}
