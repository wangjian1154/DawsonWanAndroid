package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.HomeBanner;

import java.util.List;

public interface HomeContract {

    interface View extends BaseContract.BaseView {

        void setHomeBanner(List<HomeBanner> banners);

        void setListData(BaseResponse<ArticleListBean> articleBean, boolean isRefresh);

        void showFailed(String error);

        void collectionArticle(BaseResponse result,int position);

        void unCollectionArticle(BaseResponse result,int position);

    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {

        void loadBanner();

        void loadListData(boolean isRefresh);

        void loadData(boolean isRefresh);

        void collection(int article_id,int position);

        void unCollection(int article_id,int position);
    }
}
