package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wj.base.base.BaseFragment;
import com.wj.base.utils.BaseUtils;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.AndroidTreeBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.ui.adapter.CategoryListAdapter;
import com.wj.dawsonwanandroid.ui.contract.CategoryContract;
import com.wj.dawsonwanandroid.ui.presenter.CategoryPresenter;
import com.wj.dawsonwanandroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CategoryFragment extends BaseFragment<CategoryPresenter> implements
        CategoryContract.View, OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private List<AndroidTreeBean> treeList;
    private CategoryListAdapter adapter;

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {

        treeList = new ArrayList<>();

        BaseUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getActivity()));

        adapter = new CategoryListAdapter(getActivity(),treeList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mPresenter.loadData(true);
    }


    @Override
    public CategoryPresenter createPresenter() {
        return new CategoryPresenter();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void setListData(BaseResponse<List<AndroidTreeBean>> data) {
        treeList.clear();
        if (data != null && data.getData() != null && data.getData().size() > 0) {
            treeList.addAll(data.getData());
        }
        adapter.notifyDataSetChanged();
        Utils.refreshComplete(smartRefresh);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.loadData(true);
    }
}
