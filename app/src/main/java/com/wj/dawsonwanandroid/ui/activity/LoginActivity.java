package com.wj.dawsonwanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.wj.base.base.BaseActivity;
import com.wj.base.base.SimpleActivity;
import com.wj.base.utils.BaseUtils;
import com.wj.base.utils.StringUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.ui.contract.LoginContract;
import com.wj.dawsonwanandroid.ui.presenter.LoginPresenter;
import com.wj.dawsonwanandroid.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    public static void show(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {

    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onLoginCallback(JsonObject result) {

    }

    @Override
    public void onRegisterCallback(JsonObject result) {

    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String userName = etUsername.getText().toString().trim();
                if (StringUtils.isEmpty(userName)) {
                    ToastUtils.showShort(Utils.getResourcesString(this, R.string.hint_username));
                    break;
                }
                String password = etPassword.getText().toString().trim();
                if (StringUtils.isEmpty(userName)) {
                    ToastUtils.showShort(Utils.getResourcesString(this, R.string.hint_password));
                    break;
                }
                mPresenter.login(userName, password);
                break;

            case R.id.btn_register:
                String userName2 = etUsername.getText().toString().trim();
                if (StringUtils.isEmpty(userName2)) {
                    ToastUtils.showShort(Utils.getResourcesString(this, R.string.hint_username));
                    break;
                }
                String password2 = etPassword.getText().toString().trim();
                if (StringUtils.isEmpty(userName2)) {
                    ToastUtils.showShort(Utils.getResourcesString(this, R.string.hint_password));
                    break;
                }
                mPresenter.register(userName2, password2);
                break;
        }
    }

}
