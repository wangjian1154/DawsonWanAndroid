package com.wj.dawsonwanandroid.ui.contract;

import com.wj.base.base.BaseContract;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.ProjectCategoryBean;

import java.util.List;

/**
 * Created by wj on 2018/5/18.
 */
public interface OpenSourceContract {

    interface View extends BaseContract.BaseView {

        void setCategoryData(BaseResponse<List<ProjectCategoryBean>> list);
    }

    interface Presenter extends BaseContract.AbstractPresenter<View> {

        void loadCategory();
    }
}
