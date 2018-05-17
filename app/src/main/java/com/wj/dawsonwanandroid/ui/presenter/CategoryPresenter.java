package com.wj.dawsonwanandroid.ui.presenter;

import com.wj.base.base.BasePresenter;
import com.wj.dawsonwanandroid.bean.AndroidTreeBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.net.ApiRetrofit;
import com.wj.dawsonwanandroid.net.ApiService;
import com.wj.dawsonwanandroid.ui.contract.CategoryContract;
import com.wj.dawsonwanandroid.utils.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class CategoryPresenter extends BasePresenter<CategoryContract.View> implements CategoryContract.Presenter {

    @Override
    public void loadData(boolean isRefresh) {
        ApiRetrofit.create(ApiService.class)
                .getAndroidTreeList()
                .compose(RxUtils.<BaseResponse<List<AndroidTreeBean>>>applySchedulers())
                .compose(mView.<BaseResponse<List<AndroidTreeBean>>>bindToLife())
                .subscribe(new Consumer<BaseResponse<List<AndroidTreeBean>>>() {
                    @Override
                    public void accept(BaseResponse<List<AndroidTreeBean>> result) throws Exception {
                        mView.setListData(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }
}
