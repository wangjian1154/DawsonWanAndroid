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

    @Override
    public void loadData(final boolean isRefresh) {
        ApiRetrofit.create(ApiService.class)
                .getCollectionList()
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
}
