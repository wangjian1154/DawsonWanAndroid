package com.wj.dawsonwanandroid.ui.presenter;

import com.wj.base.base.BasePresenter;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.net.ApiRetrofit;
import com.wj.dawsonwanandroid.net.ApiService;
import com.wj.dawsonwanandroid.ui.contract.CollectionContact;
import com.wj.dawsonwanandroid.utils.RxUtils;

import io.reactivex.functions.Consumer;

/**
 * Created by wj on 2018/7/2.
 */
public class CollectionPresenter extends BasePresenter<CollectionContact.View> implements CollectionContact.Presenter {

    private int page = 0;

    @Override
    public void loadListData(final boolean isRefresh) {
        ApiRetrofit.create(ApiService.class)
                .getCollectionList(page)
                .compose(RxUtils.<BaseResponse<ArticleBean>>applySchedulers())
                .compose(mView.<BaseResponse<ArticleBean>>bindToLife())
                .subscribe(new Consumer<BaseResponse<ArticleBean>>() {
                    @Override
                    public void accept(BaseResponse<ArticleBean> articleBean) throws Exception {
                        mView.setListData(articleBean, isRefresh);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void collection(int article_id, final int position) {
        ApiRetrofit.create(ApiService.class)
                .collectionArticle(article_id)
                .compose(RxUtils.<BaseResponse>applySchedulers())
                .compose(mView.<BaseResponse>bindToLife())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse result) throws Exception {
                        mView.collectionArticle(result,position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void unCollection(int article_id, final int position) {
        ApiRetrofit.create(ApiService.class)
                .unCollectionArticle(article_id)
                .compose(RxUtils.<BaseResponse>applySchedulers())
                .compose(mView.<BaseResponse>bindToLife())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse result) throws Exception {
                        mView.unCollectionArticle(result,position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable.getMessage());
                    }
                });
    }
    @Override
    public void loadData(boolean isRefresh) {
        if (isRefresh) {
            page = 0;
            loadListData(isRefresh);
        } else {
            page++;
            loadListData(isRefresh);
        }
    }
}
