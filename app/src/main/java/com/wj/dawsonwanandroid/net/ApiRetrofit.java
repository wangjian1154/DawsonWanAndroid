package com.wj.dawsonwanandroid.net;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.GsonBuilder;
import com.wj.base.Initialization;
import com.wj.base.utils.NetworkUtils;
import com.wj.dawsonwanandroid.core.MyApp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wj on 2018/1/7.
 */

public class ApiRetrofit {

    private static ApiRetrofit mInstance;
    private static Retrofit mRetrofit;

    private static ApiRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (ApiRetrofit.class) {
                if (mInstance == null)
                    mInstance = new ApiRetrofit();
            }
        }
        return mInstance;
    }

    private ApiRetrofit() {
        //设置缓存路径
        File httpCacheDirectory = new File(Initialization.getContext().getCacheDir(), "RetrofitCache");
        int cacheSize = 1024 * 1024 * 50; // 50 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);



        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)//添加日志
                .addInterceptor(mHeaderInterceptor)//添加Header
                .addInterceptor(mCacheInterceptor)//添加缓存
                .addNetworkInterceptor(mCacheInterceptor)
                .cookieJar(MyApp.getCookie())
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)//设置连接超时20秒
                .build();

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))//支持Gson转换
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持RxJava
                .baseUrl(ApiConstants.API_SERVER)
                .client(mClient)
                .build();
    }

    public static <T> T create(Class<T> clazz) {
        getInstance();
        return mRetrofit.create(clazz);
    }


    //缓存配置
    private Interceptor mCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            if (!NetworkUtils.isNetworkAvailable(Initialization.getContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE).build();
            } else {
                request = request.newBuilder()
                        //网络可用 强制从网络获取数据
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            }

            Response response = chain.proceed(request);
            if (NetworkUtils.isNetworkAvailable(Initialization.getContext())) {
                int maxAge = 60; //缓存一分钟
                String cacheControl = "public,max-age=" + maxAge;
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                int maxStale = 60 * 60 * 24 * 7 * 4;  //4周
                return response.newBuilder()
                        .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                        .removeHeader("Pragma").build();
            }
        }
    };


    private Interceptor mHeaderInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder = originalRequest.newBuilder();
            //设置具体的Head内容
            builder.header("timestamp", System.currentTimeMillis() + "");
            Request.Builder requestBuilder =
                    builder.method(originalRequest.method(), originalRequest.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };
}
