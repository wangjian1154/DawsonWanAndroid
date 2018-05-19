package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.wj.base.base.SimpleFragment;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.core.JumpModel;
import com.wj.dawsonwanandroid.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonFragment extends SimpleFragment {

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person;
    }

    @OnClick({R.id.rl_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_login:
                JumpModel.getInstance().jumpLogin(getActivity());
                break;
        }
    }
}
