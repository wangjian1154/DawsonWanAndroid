package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.HomeBanner;

import java.util.List;

public interface HomeContract {

    interface View extends BaseContract.BaseView {

        void setHomeBanner(List<HomeBanner> banners);

        void setListData(BaseResponse<List<ArticleBean>> articleBean,boolean isRefresh);

        void showFailed(String error);

    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {

        void loadBanner();

        void loadListData(boolean isRefresh);

        void loadData(boolean isRefresh);
    }
}
