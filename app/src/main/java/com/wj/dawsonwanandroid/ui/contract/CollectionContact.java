package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;

/**
 * Created by wj on 2018/7/2.
 */
public interface CollectionContact {

    interface View extends BaseContract.BaseView {

        void setListData(BaseResponse<ArticleListBean> articleBean, boolean isRefresh);

        void collectionArticle(BaseResponse result,int position);

        void unCollectionArticle(BaseResponse result,int position);

    }

    interface Presenter extends BaseContract.AbstractPresenter<CollectionContact.View> {

        void loadData(boolean isRefresh);

        void loadListData(boolean isRefresh);

        void collection(int article_id,int position);

        void unCollection(int article_id,int position);

    }
}
