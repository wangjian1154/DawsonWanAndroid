package com.wj.dawsonwanandroid.core;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.wj.base.Initialization;
import com.wj.base.utils.SPUtils;
import com.wj.dawsonwanandroid.bean.UserBean;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wj on 2018/5/11.
 */
public class MyApp extends Application {

    private static Context context;
    private static ClearableCookieJar cookieJar;

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

    /**
     * 登出
     *
     * @return
     */
    public static void loginOut() {
        if (MyApp.isLogined() != null && MyApp.isLogined() instanceof UserBean) {
            SPUtils.getInstance().remove(Constants.SP_KEY.USER_INFO);

            clearCookie();

            EventBus.getDefault().post(Message.obtain(new Handler(Looper.getMainLooper()),
                    Constants.Key_EventBus_Msg.EXIT_LOGIN, null));
        }
    }

    public static ClearableCookieJar getCookie() {
        if (cookieJar==null){
            cookieJar = new PersistentCookieJar(
                    new SetCookieCache(), new SharedPrefsCookiePersistor(Initialization.getContext()));
        }
        return cookieJar;
    }

    public static void clearCookie() {
        if (cookieJar != null) {
            cookieJar.clear();
        }
    }
}
