package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;

/**
 * Created by wj on 2018/5/22.
 */
public interface KnowledgeContract {

    interface View extends BaseContract.BaseView {
        void setListData(boolean isRefresh, BaseResponse<ArticleListBean> result);
    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {
        void loadData(boolean isRefresh, int cid);
    }
}
