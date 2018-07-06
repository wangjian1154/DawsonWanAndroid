package com.wj.dawsonwanandroid.core;

import com.wj.dawsonwanandroid.dao.DaoMaster;
import com.wj.dawsonwanandroid.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by wj on 2018/7/4.
 */
public class DBManager {

    private DaoMaster.DevOpenHelper mHelper;
    private DaoSession daoSession;
    private Database database;

    public DBManager() {
        mHelper = new DaoMaster.DevOpenHelper(MyApp.getInstance(), Constants.DB.DB_NAME);
        database = mHelper.getWritableDb();
        DaoMaster mDaoMaster = new DaoMaster(database);
        daoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void close() {
        mHelper.close();
        database.close();
        daoSession.clear();
    }
}
