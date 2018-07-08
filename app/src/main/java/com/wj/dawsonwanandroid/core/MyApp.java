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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.wj.base.Initialization;
import com.wj.base.utils.SPUtils;
import com.wj.dawsonwanandroid.R;
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

        ThirdParty.init();

        new Initialization(this);

    }



    public static Context getInstance() {
        return context;
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
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
