package com.wj.dawsonwanandroid.core;

import android.content.Context;

import com.wj.dawsonwanandroid.bean.AndroidTreeBean;
import com.wj.dawsonwanandroid.ui.activity.CollectionActivity;
import com.wj.dawsonwanandroid.ui.activity.KnowledgeActivity;
import com.wj.dawsonwanandroid.ui.activity.LoginActivity;
import com.wj.dawsonwanandroid.ui.activity.RegisterActivity;
import com.wj.dawsonwanandroid.ui.activity.WebViewActivity;

public class JumpModel {


    private static JumpModel instance;

    public static JumpModel getInstance() {
        if (instance == null)
            instance = new JumpModel();
        return instance;
    }

    public static void jumpWebActivity(Context context, String url) {
        WebViewActivity.show(context, url);
    }

    public static void jumpLogin(Context context) {
        LoginActivity.show(context);
    }

    public static void jumpRegister(Context context) {
        RegisterActivity.show(context);
    }

    public static void jumpKnowledge(Context context, AndroidTreeBean bean) {
        KnowledgeActivity.show(context, bean);
    }

    public static void jumpCollection(Context context) {
        CollectionActivity.show(context);
    }
}
