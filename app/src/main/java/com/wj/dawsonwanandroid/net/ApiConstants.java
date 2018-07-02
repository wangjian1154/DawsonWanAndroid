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

    //注册
    public static final String API_REGISTER = "/user/register";

    //登录
    public static final String API_LOGIN = "/user/login";

    //知识体系下文章
    public static final String API_KNOWLEDGE = "/article/list/{page}/json";

    //收藏站内文章
    public static final String API_ARTICLE_COLLECTION="/lg/collect/{article_id}/json";

    //取消收藏
    public static final String API_UN_COLLECTION="/lg/uncollect_originId/{article_id}/json";

    //我的收藏列表
    public static final String API_COLLECTION_LIST="/lg/collect/list/0/json";
}
