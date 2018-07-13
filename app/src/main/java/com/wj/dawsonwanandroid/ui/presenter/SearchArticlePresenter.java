package com.wj.dawsonwanandroid.ui.presenter;

import com.wj.base.base.BasePresenter;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.net.ApiRetrofit;
import com.wj.dawsonwanandroid.net.ApiService;
import com.wj.dawsonwanandroid.ui.contract.SearchArticleContract;
import com.wj.dawsonwanandroid.utils.RxUtils;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;

/**
 * Created by wj on 2018/7/13.
 */
public class SearchArticlePresenter extends BasePresenter<SearchArticleContract.View> implements SearchArticleContract.Presenter {

    private int page=0;

    @Override
    public void search(final boolean isRefresh, String key) {
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        ApiRetrofit.create(ApiService.class)
                .searchArticle(page, key)
                .compose(RxUtils.<BaseResponse<ArticleListBean>>applySchedulers())
                .compose(mView.<BaseResponse<ArticleListBean>>bindToLife())
                .subscribe(new Consumer<BaseResponse<ArticleListBean>>() {
                    @Override
                    public void accept(BaseResponse<ArticleListBean> result) throws Exception {
                        mView.onSearchResult(isRefresh,result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable.getMessage());
                    }
                });


    }

}
