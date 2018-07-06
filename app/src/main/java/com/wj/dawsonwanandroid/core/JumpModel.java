package com.wj.dawsonwanandroid.core;

import android.content.Context;

import com.wj.dawsonwanandroid.bean.AndroidTreeBean;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.dao.ArticleBeanDao;
import com.wj.dawsonwanandroid.ui.activity.ArticleDetailViewActivity;
import com.wj.dawsonwanandroid.ui.activity.CollectionActivity;
import com.wj.dawsonwanandroid.ui.activity.KnowledgeActivity;
import com.wj.dawsonwanandroid.ui.activity.LoginActivity;
import com.wj.dawsonwanandroid.ui.activity.RegisterActivity;
import com.wj.dawsonwanandroid.ui.activity.VisitHistoryActivity;

import java.util.List;

public class JumpModel {


    private static JumpModel instance;

    public static JumpModel getInstance() {
        if (instance == null)
            instance = new JumpModel();
        return instance;
    }

    public void jumpArticleDetailActivity(Context context, ArticleBean articleBean) {

        DBManager dbManager = new DBManager();
        List<ArticleBean> list = dbManager.getDaoSession().queryBuilder(ArticleBean.class)
                .where(ArticleBeanDao.Properties.Id.eq(articleBean.id)).list();
        if (list == null || list.size() == 0) {
            dbManager.getDaoSession().insert(articleBean);
        }
        dbManager.close();

        String url = articleBean.link;
        ArticleDetailViewActivity.show(context, url);
    }

    public void jumpWebView(Context context, String url) {
        ArticleDetailViewActivity.show(context, url);
    }

    public void jumpLogin(Context context) {
        LoginActivity.show(context);
    }

    public void jumpRegister(Context context) {
        RegisterActivity.show(context);
    }

    public void jumpKnowledge(Context context, AndroidTreeBean bean) {
        KnowledgeActivity.show(context, bean);
    }

    public void jumpCollection(Context context) {
        CollectionActivity.show(context);
    }

    public void jumpVisitHistory(Context context) {
        VisitHistoryActivity.show(context);
    }
}
