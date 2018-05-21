package com.wj.dawsonwanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.wj.base.base.BaseActivity;
import com.wj.base.utils.SPUtils;
import com.wj.base.utils.StatusBarUtil;
import com.wj.base.utils.StringUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.base.view.TitleBar;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.UserBean;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.core.JumpModel;
import com.wj.dawsonwanandroid.ui.contract.UserContract;
import com.wj.dawsonwanandroid.ui.presenter.UserPresenter;
import com.wj.dawsonwanandroid.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<UserPresenter> implements UserContract.View {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.title_bar)
    TitleBar titleBar;

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
        titleBar.setTitle(Utils.getResourcesString(this, R.string.login));
        titleBar.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    public void onLoginCallback(BaseResponse<UserBean> result) {
        if (result.getErrorCode() != Constants.RESPONSE.SUCCESS) {
            ToastUtils.showShort(result.getErrorMsg());
        } else {
            ToastUtils.showShort(Utils.getResourcesString(this, R.string.login_success));
            UserBean data = result.getData();
            if (data != null)
                SPUtils.getInstance().put(Constants.SP_KEY.USER_INFO, data);
            EventBus.getDefault().post(Message.obtain(new Handler(Looper.getMainLooper()),
                    Constants.Key_EventBus_Msg.LOGIN_SUCCESS, null));
            finish();
        }
    }

    @Override
    public void onRegisterCallback(BaseResponse result) {

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

                if (userName.length() <= 6 || password.length() <= 6) {
                    ToastUtils.showShort(Utils.getResourcesString(this, R.string.register_hint));
                    break;
                }

                mPresenter.login(userName, password);
                break;

            case R.id.btn_register:
                JumpModel.getInstance().jumpRegister(this);
                break;
        }
    }

}
