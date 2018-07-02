package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wj.base.base.SimpleFragment;
import com.wj.base.utils.ImageLoadUtils;
import com.wj.base.utils.SPUtils;
import com.wj.base.utils.StringUtils;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.UserBean;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.core.JumpModel;
import com.wj.dawsonwanandroid.core.MyApp;
import com.wj.dawsonwanandroid.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonFragment extends SimpleFragment {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.ll_exit_login)
    LinearLayout llExitLogin;


    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {

        loadData();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person;
    }

    @OnClick({R.id.rl_login, R.id.piv_exit_login, R.id.piv_collection, R.id.piv_browse_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_login:
                if (MyApp.isLogined() == null) {
                    JumpModel.getInstance().jumpLogin(getActivity());
                }
                break;

            case R.id.piv_exit_login:

                MyApp.loginOut();

                break;

            case R.id.piv_collection:
                if (MyApp.checkLogin(getActivity())) {
                    JumpModel.getInstance().jumpCollection(getActivity());
                }
                break;

            case R.id.piv_browse_history:
                if (MyApp.checkLogin(getActivity())) {

                }
                break;
        }
    }

    @Override
    public void onEventMainThread(Message msg) {
        super.onEventMainThread(msg);
        switch (msg.what) {
            case Constants.Key_EventBus_Msg.LOGIN_SUCCESS:

                loadData();
                break;

            case Constants.Key_EventBus_Msg.EXIT_LOGIN:

                loadData();
                break;
        }
    }

    private void loadData() {
        llExitLogin.setVisibility(MyApp.isLogined() == null ? View.GONE : View.VISIBLE);

        UserBean userBean = MyApp.isLogined();
        if (userBean != null) {
            tvUsername.setText(userBean.getUsername());
            if (!StringUtils.isEmpty(userBean.getIcon())) {
                ImageLoadUtils.display(getActivity(), userBean.getIcon(), ivAvatar);
            } else {
                ivAvatar.setImageResource(R.drawable.ic_avatar_default);
            }
        } else {
            tvUsername.setText(Utils.getResourcesString(getActivity(), R.string.click_login));
            ivAvatar.setImageResource(R.drawable.ic_avatar_default);
        }
    }
}
