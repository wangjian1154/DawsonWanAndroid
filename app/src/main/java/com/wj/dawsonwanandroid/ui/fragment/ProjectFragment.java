package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wj.base.base.BaseFragment;
import com.wj.base.utils.BaseUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.bean.ProjectListBean;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.ui.adapter.ProjectListAdapter;
import com.wj.dawsonwanandroid.ui.contract.ProjectContract;
import com.wj.dawsonwanandroid.ui.presenter.ProjectPresenter;
import com.wj.dawsonwanandroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wj on 2018/5/18.
 */
public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.View, OnRefreshLoadmoreListener {

    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<ProjectListBean.DatasBean> list;
    private ProjectListAdapter adapter;
    private int cId;

    public static ProjectFragment newInstance(int cid) {
        ProjectFragment projectFragment = new ProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.KEY, cid);
        projectFragment.setArguments(bundle);
        return projectFragment;
    }

    @Override
    public ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    public void setListData(boolean isRefresh, BaseResponse<ProjectListBean> result) {
        if (isRefresh) list.clear();
        if (result != null && result.getData() != null & result.getData().getDatas() != null
                && result.getData().getDatas().size() > 0) {
            list.addAll(result.getData().getDatas());
        }
        adapter.notifyDataSetChanged();
        Utils.refreshComplete(smartRefresh);
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        cId = bundle.getInt(Constants.Key.KEY);

        list = new ArrayList<>();

        adapter = new ProjectListAdapter(list);
        BaseUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        smartRefresh.setOnRefreshLoadmoreListener(this);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mPresenter.loadData(true, cId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.loadData(false, cId);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.loadData(true, cId);
    }
}
