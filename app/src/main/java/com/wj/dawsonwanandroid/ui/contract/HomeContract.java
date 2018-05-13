package com.wj.dawsonwanandroid.ui.contract;

import com.wj.dawsonwanandroid.base.BaseContract;
import com.wj.dawsonwanandroid.bean.HomeBanner;

import java.util.List;

public interface HomeContract {

    interface View extends BaseContract.BaseView {

        void setHomeBanner(List<HomeBanner> banners);

        void setListData();

        void showFailed(String error);

    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {

        void loadBanner();

        void loadListData();

        void loadData(boolean isRefresh);
    }
}
