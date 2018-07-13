package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;

/**
 * Created by wj on 2018/7/13.
 */
public interface SearchArticleContract {

    interface View extends BaseContract.BaseView {
        void onSearchResult(boolean isRefresh, BaseResponse<ArticleListBean> result);
    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {
        void search(boolean isRefresh, String key);
    }
}
