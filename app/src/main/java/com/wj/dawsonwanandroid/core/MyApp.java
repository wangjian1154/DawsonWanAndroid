package com.wj.dawsonwanandroid.core;

import android.app.Application;
import android.content.Context;

import com.wj.base.Initialization;
import com.wj.base.utils.SPUtils;
import com.wj.dawsonwanandroid.bean.UserBean;

/**
 * Created by wj on 2018/5/11.
 */
public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        new Initialization(this);
    }

    public static Context getInstance() {
        return context;
    }

    public static UserBean isLogined() {
        Object object = SPUtils.getInstance().getObject(Constants.SP_KEY.USER_INFO);
        if (object != null && object instanceof UserBean) {
            return (UserBean) object;
        }
        return null;
    }

    public static boolean checkLogin(Context activity) {
        Object object = SPUtils.getInstance().getObject(Constants.SP_KEY.USER_INFO);
        if (object == null) {
            JumpModel.getInstance().jumpLogin(activity);
            return false;
        }
        return true;
    }
}
