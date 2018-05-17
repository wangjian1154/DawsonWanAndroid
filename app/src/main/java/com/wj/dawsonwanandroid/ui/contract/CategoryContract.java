package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.wj.dawsonwanandroid.bean.AndroidTreeBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;

import java.util.List;

/**
 * Created by wj on 2018/5/17.
 */
public interface CategoryContract {

    interface View extends BaseContract.BaseView {
        void setListData(BaseResponse<List<AndroidTreeBean>> data);
    }

    interface Presenter extends BaseContract.AbstractPresenter<CategoryContract.View> {

        void loadData(boolean isRefresh);

    }
}
