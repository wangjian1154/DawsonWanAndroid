package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.ProjectListBean;

import java.util.List;

/**
 * Created by wj on 2018/5/18.
 */
public interface ProjectContract {

    interface View extends BaseContract.BaseView {
        void setListData(boolean isRefresh,BaseResponse<ProjectListBean> result);
    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {

        void loadData(boolean isRefresh,int cid);
    }
}
