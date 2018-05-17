package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wj.base.base.SimpleFragment;
import com.wj.base.utils.BaseUtils;
import com.wj.dawsonwanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryFragment extends SimpleFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        BaseUtils.configRecyclerView(recyclerView,new LinearLayoutManager(getActivity()));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }



}
