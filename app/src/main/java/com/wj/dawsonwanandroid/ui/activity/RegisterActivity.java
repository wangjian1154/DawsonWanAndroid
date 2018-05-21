package com.wj.dawsonwanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.wj.base.base.BaseActivity;
import com.wj.base.utils.StatusBarUtil;
import com.wj.base.utils.StringUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.base.view.TitleBar;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.ui.contract.UserContract;
import com.wj.dawsonwanandroid.ui.presenter.UserPresenter;
import com.wj.dawsonwanandroid.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<UserPresenter> implements UserContract.View {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repassword)
    EditText etRepassword;
    @BindView(R.id.title_bar)
    TitleBar titleBar;

    public static void show(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }


    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        titleBar.setTitle(Utils.getResourcesString(this, R.string.register_title));
        titleBar.setTitleTextColor(Color.WHITE);
        titleBar.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.btn_register})
    public void onCLick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
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
                String repassword = etRepassword.getText().toString().trim();
                if (StringUtils.isEmpty(userName)) {
                    ToastUtils.showShort(Utils.getResourcesString(this, R.string.hint_password));
                    break;
                }

                if (userName.length() <= 6 || password.length() <= 6 || repassword.length() <= 6) {
                    ToastUtils.showShort(Utils.getResourcesString(this, R.string.register_hint));
                    break;
                }

                if (!password.equals(repassword)) {
                    etPassword.setText("");
                    etRepassword.setText("");
                    ToastUtils.showShort(Utils.getResourcesString(this, R.string.password_disagree));
                    break;
                }

                mPresenter.register(userName, password, repassword);
                break;
        }
    }


    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    public void onLoginCallback(BaseResponse result) {

    }

    @Override
    public void onRegisterCallback(BaseResponse result) {
        if (result.getErrorCode() != Constants.RESPONSE.SUCCESS) {
            ToastUtils.showShort(result.getErrorMsg());
        } else {
            ToastUtils.showShort(Utils.getResourcesString(this, R.string.register_success));
            finish();
        }
    }
}
