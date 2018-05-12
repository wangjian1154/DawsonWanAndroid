package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.youth.banner.Banner;

import java.util.List;

public interface HomeContract {

    interface View extends BaseContract.BaseView {

        void setHomeBanner(List<Banner> banner);

        void setListData();

    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {

        void loadBanner();

        void loadListData();
    }
}
