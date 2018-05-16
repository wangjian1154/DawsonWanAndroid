package com.wj.dawsonwanandroid.net;

import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.HomeBanner;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    /**
     * 首页Banner
     *
     * @return
     */
    @GET(ApiConstants.API_HOME_BANNER)
    Observable<BaseResponse<List<HomeBanner>>> getHomeBanners();

    @GET(ApiConstants.API_HOME_ARTICLE)
    Observable<BaseResponse<ArticleBean>> getArticleList(@Path("page") int page);

}
