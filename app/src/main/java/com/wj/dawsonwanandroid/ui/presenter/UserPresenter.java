package com.wj.dawsonwanandroid.ui.presenter;

import com.wj.base.base.BasePresenter;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.UserBean;
import com.wj.dawsonwanandroid.net.ApiRetrofit;
import com.wj.dawsonwanandroid.net.ApiService;
import com.wj.dawsonwanandroid.ui.contract.UserContract;
import com.wj.dawsonwanandroid.utils.RxUtils;

import io.reactivex.functions.Consumer;

public class UserPresenter extends BasePresenter<UserContract.View> implements UserContract.Presenter {

    @Override
    public void login(String username, String password) {
        ApiRetrofit.create(ApiService.class)
                .login(username, password)
                .compose(RxUtils.<BaseResponse<UserBean>>applySchedulers())
                .compose(mView.<BaseResponse<UserBean>>bindToLife())
                .subscribe(new Consumer<BaseResponse<UserBean>>() {
                    @Override
                    public void accept(BaseResponse<UserBean> result) throws Exception {
                        mView.onLoginCallback(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void register(String username, String password, String repassword) {
        ApiRetrofit.create(ApiService.class)
                .register(username, password, repassword)
                .compose(RxUtils.<BaseResponse>applySchedulers())
                .compose(mView.<BaseResponse>bindToLife())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse result) throws Exception {
                        mView.onRegisterCallback(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable.getMessage());
                    }
                });
    }
}
