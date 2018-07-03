package com.wj.dawsonwanandroid.net;

import com.wj.dawsonwanandroid.bean.AndroidTreeBean;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.HomeBanner;
import com.wj.dawsonwanandroid.bean.ProjectCategoryBean;
import com.wj.dawsonwanandroid.bean.ProjectListBean;
import com.wj.dawsonwanandroid.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 首页Banner
     *
     * @return
     */
    @GET(ApiConstants.API_HOME_BANNER)
    Observable<BaseResponse<List<HomeBanner>>> getHomeBanners();

    @GET(ApiConstants.API_HOME_ARTICLE)
    Observable<BaseResponse<ArticleListBean>> getArticleList(@Path("page") int page);

    @GET(ApiConstants.API_ANDROID_TREE)
    Observable<BaseResponse<List<AndroidTreeBean>>> getAndroidTreeList();

    @GET(ApiConstants.API_PROJECT_OPEN)
    Observable<BaseResponse<List<ProjectCategoryBean>>> getProjectCategory();

    @GET(ApiConstants.API_PROJECT_LIST)
    Observable<BaseResponse<ProjectListBean>> getProjectList(@Path("page") int page, @Query("cid") int cid);

    @POST(ApiConstants.API_REGISTER)
    Observable<BaseResponse> register(@Query("username") String username,
                                      @Query("password") String password, @Query("repassword") String repassword);

    @POST(ApiConstants.API_LOGIN)
    Observable<BaseResponse<UserBean>> login(@Query("username") String username, @Query("password") String password);

    @GET(ApiConstants.API_KNOWLEDGE)
    Observable<BaseResponse<ArticleListBean>> getKnowledgeList(@Path("page") int page, @Query("cid") int cid);

    @POST(ApiConstants.API_ARTICLE_COLLECTION)
    Observable<BaseResponse> collectionArticle(@Path("article_id") int article_id);

    @POST(ApiConstants.API_UN_COLLECTION)
    Observable<BaseResponse> unCollectionArticle(@Path("article_id") int article_id);

    @GET(ApiConstants.API_COLLECTION_LIST)
    Observable<BaseResponse<ArticleListBean>> getCollectionList(@Path("page") int page);
}
