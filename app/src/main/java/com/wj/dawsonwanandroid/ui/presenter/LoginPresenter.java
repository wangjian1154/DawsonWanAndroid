package com.wj.dawsonwanandroid.ui.presenter;

import com.google.gson.JsonObject;
import com.wj.base.base.BasePresenter;
import com.wj.dawsonwanandroid.net.ApiRetrofit;
import com.wj.dawsonwanandroid.net.ApiService;
import com.wj.dawsonwanandroid.ui.contract.LoginContract;
import com.wj.dawsonwanandroid.utils.RxUtils;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String username, String password) {
        ApiRetrofit.create(ApiService.class)
                .login(username, password)
                .compose(RxUtils.<JsonObject>applySchedulers())
                .compose(mView.<JsonObject>bindToLife())
                .subscribe(new Consumer<JsonObject>() {
                    @Override
                    public void accept(JsonObject result) throws Exception {
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
    public void register(String username, String password) {
        ApiRetrofit.create(ApiService.class)
                .register(username, password)
                .compose(RxUtils.<JsonObject>applySchedulers())
                .compose(mView.<JsonObject>bindToLife())
                .subscribe(new Consumer<JsonObject>() {
                    @Override
                    public void accept(JsonObject result) throws Exception {
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
