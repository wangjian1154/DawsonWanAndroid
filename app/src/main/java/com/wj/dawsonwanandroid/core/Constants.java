package com.wj.dawsonwanandroid.core;


public class Constants {

    public interface Key {
        String BUNDLE = "BUNDLE";
        String KEY = "key";
    }

    public interface RESPONSE {
        int SUCCESS = 0;
    }

    public interface Key_EventBus_Msg {
        int LOGIN_SUCCESS = 1;
        int EXIT_LOGIN = 2;
    }

    public interface SP_KEY {
        String USER_INFO = "user_info";
    }

    public interface DB {
        String DB_NAME = "wan_android";
        String TABLE_VISIT_HISTORY="visit_history";
    }
}
