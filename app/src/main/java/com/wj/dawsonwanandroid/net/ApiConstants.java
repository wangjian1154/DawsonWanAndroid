package com.wj.dawsonwanandroid.net;


public class ApiConstants {

    public static final String API_SERVER = "http://www.wanandroid.com";

    //每页数据量
    public static final int LIMIT = 20;

    //首页Banner
    public static final String API_HOME_BANNER = "/banner/json";

    //首页文章
    public static final String API_HOME_ARTICLE = "/article/list/{page}/json";

    //知识体系
    public static final String API_ANDROID_TREE = "/tree/json";

    //项目分类
    public static final String API_PROJECT_OPEN = "/project/tree/json";

    //项目列表数据
    public static final String API_PROJECT_LIST = "/project/list/{page}/json";
}
