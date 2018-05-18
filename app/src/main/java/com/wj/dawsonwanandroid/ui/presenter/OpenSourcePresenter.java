package com.wj.dawsonwanandroid.ui.presenter;

import com.wj.base.base.BasePresenter;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.ProjectCategoryBean;
import com.wj.dawsonwanandroid.net.ApiRetrofit;
import com.wj.dawsonwanandroid.net.ApiService;
import com.wj.dawsonwanandroid.ui.contract.OpenSourceContract;
import com.wj.dawsonwanandroid.utils.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by wj on 2018/5/18.
 */
public class OpenSourcePresenter extends BasePresenter<OpenSourceContract.View> implements OpenSourceContract.Presenter {

    @Override
    public void loadCategory() {
        ApiRetrofit.create(ApiService.class)
                .getProjectCategory()
                .compose(RxUtils.<BaseResponse<List<ProjectCategoryBean>>>applySchedulers())
                .compose(mView.<BaseResponse<List<ProjectCategoryBean>>>bindToLife())
                .subscribe(new Consumer<BaseResponse<List<ProjectCategoryBean>>>() {
                    @Override
                    public void accept(BaseResponse<List<ProjectCategoryBean>> result) throws Exception {
                        mView.setCategoryData(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable.getMessage());
                    }
                });
    }
}
