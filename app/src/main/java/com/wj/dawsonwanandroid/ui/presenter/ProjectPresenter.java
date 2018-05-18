package com.wj.dawsonwanandroid.ui.presenter;

import com.wj.base.base.BasePresenter;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.ProjectListBean;
import com.wj.dawsonwanandroid.net.ApiRetrofit;
import com.wj.dawsonwanandroid.net.ApiService;
import com.wj.dawsonwanandroid.ui.contract.ProjectContract;
import com.wj.dawsonwanandroid.utils.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by wj on 2018/5/18.
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    private int page = 0;

    @Override
    public void loadData(final boolean isRefresh, int cid) {
        page = isRefresh ? 0 : page++;
        ApiRetrofit.create(ApiService.class)
                .getProjectList(page, cid)
                .compose(RxUtils.<BaseResponse<ProjectListBean>>applySchedulers())
                .compose(mView.<BaseResponse<ProjectListBean>>bindToLife())
                .subscribe(new Consumer<BaseResponse<ProjectListBean>>() {
                    @Override
                    public void accept(BaseResponse<ProjectListBean> result) throws Exception {
                        mView.setListData(isRefresh,result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable.getMessage());
                    }
                });
    }
}
