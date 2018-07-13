package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.UserBean;

public interface UserContract {

    interface View extends BaseContract.BaseView {

        void onLoginCallback(BaseResponse<UserBean> result);

        void onRegisterCallback(BaseResponse result);
    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {

        void login(String username, String password);

        void register(String username, String password,String repassword);
    }
}
