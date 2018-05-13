package com.wj.dawsonwanandroid.ui.presenter;

import com.wj.dawsonwanandroid.base.BasePresenter;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.HomeBanner;
import com.wj.dawsonwanandroid.net.ApiRetrofit;
import com.wj.dawsonwanandroid.net.ApiService;
import com.wj.dawsonwanandroid.ui.contract.HomeContract;
import com.wj.dawsonwanandroid.utils.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private int page = 0;

    @Override
    public void loadBanner() {
        ApiRetrofit.create(ApiService.class)
                .getHomeBanners()
                .compose(RxUtils.<BaseResponse<List<HomeBanner>>>applySchedulers())
                .compose(mView.<BaseResponse<List<HomeBanner>>>bindToLife())
                .subscribe(new Consumer<BaseResponse<List<HomeBanner>>>() {
                    @Override
                    public void accept(BaseResponse<List<HomeBanner>> listBaseResponse) throws Exception {
                        mView.setHomeBanner(listBaseResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed(throwable.getMessage());
                    }
                });
    }

    @Override
    public void loadListData() {

    }

    @Override
    public void loadData(boolean isRefresh) {
        if (isRefresh) {
            page = 0;
            loadBanner();
            loadListData();
        } else {
            page++;
            loadListData();
        }
    }
}
