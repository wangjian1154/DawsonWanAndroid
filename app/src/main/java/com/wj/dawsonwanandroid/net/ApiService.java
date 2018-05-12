package com.wj.dawsonwanandroid.net;

import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.HomeBanner;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    /**
     * 首页Banner
     *
     * @return
     */
    @GET(ApiConstants.API_HOME_BANNER)
    Observable<BaseResponse<HomeBanner>> getHomeBanners();

}
