package com.wj.dawsonwanandroid.core;

import android.content.Context;

import com.wj.dawsonwanandroid.bean.AndroidTreeBean;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.dao.DBManager;
import com.wj.dawsonwanandroid.ui.activity.ArticleDetailViewActivity;
import com.wj.dawsonwanandroid.ui.activity.CollectionActivity;
import com.wj.dawsonwanandroid.ui.activity.KnowledgeActivity;
import com.wj.dawsonwanandroid.ui.activity.LoginActivity;
import com.wj.dawsonwanandroid.ui.activity.RegisterActivity;

public class JumpModel {


    private static JumpModel instance;

    public static JumpModel getInstance() {
        if (instance == null)
            instance = new JumpModel();
        return instance;
    }

    public static void jumpArticleDetailActivity(Context context, ArticleBean articleBean) {
//        DBManager.insertVisitHistory(articleBean);
        String url = articleBean.link;
        ArticleDetailViewActivity.show(context, url);
    }

    public static void jumpWebView(Context context,String url){
        ArticleDetailViewActivity.show(context, url);
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
