package com.wj.dawsonwanandroid.ui.presenter;

import com.wj.base.base.BasePresenter;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.net.ApiRetrofit;
import com.wj.dawsonwanandroid.net.ApiService;
import com.wj.dawsonwanandroid.ui.contract.KnowledgeContract;
import com.wj.dawsonwanandroid.utils.RxUtils;

import io.reactivex.functions.Consumer;

/**
 * Created by wj on 2018/5/22.
 */
public class KnowledgePresenter extends BasePresenter<KnowledgeContract.View> implements KnowledgeContract.Presenter {

    private int page = 0;

    @Override
    public void loadData(final boolean isRefresh, int cid) {
        if (isRefresh) {
            page = 0;
        } else {
            page++;
        }
        ApiRetrofit.create(ApiService.class)
                .getKnowledgeList(page, cid)
                .compose(RxUtils.<BaseResponse<ArticleBean>>applySchedulers())
                .compose(mView.<BaseResponse<ArticleBean>>bindToLife())
                .subscribe(new Consumer<BaseResponse<ArticleBean>>() {
                    @Override
                    public void accept(BaseResponse<ArticleBean> result) throws Exception {
                        mView.setListData(isRefresh, result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable.getMessage());
                    }
                });

    }
}
