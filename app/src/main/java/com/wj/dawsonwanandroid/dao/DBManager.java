package com.wj.dawsonwanandroid.dao;

import android.content.Context;

import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.core.Constants;

import java.util.List;

/**
 * Created by wj on 2018/7/3.
 */
public class DBManager {

    private static DaoMaster.DevOpenHelper mHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession daoSession;

    public static void initDatabase(Context context) {
        if (mHelper == null || daoSession == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, Constants.DB.DB_NAME);
            mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
            daoSession = mDaoMaster.newSession();
        }
    }

    public static void insertVisitHistory(ArticleBean article) {
        daoSession.insert(article);
    }

    public static void deleteVisitHistory(ArticleBean article) {
        daoSession.delete(article);
    }

    public static List<ArticleBean> findVisitHistory() {
        List<ArticleBean> articleBeans = daoSession.queryBuilder(ArticleBean.class).list();
        return articleBeans;
    }

    public static void close() {
        daoSession.clear();
        daoSession = null;
        mHelper.close();
        mHelper = null;
    }

}
