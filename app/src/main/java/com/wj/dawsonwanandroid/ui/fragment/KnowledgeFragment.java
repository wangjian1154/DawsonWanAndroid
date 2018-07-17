package com.wj.dawsonwanandroid.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wj.base.base.BaseFragment;
import com.wj.base.utils.BaseUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.base.view.recyclerview.CoreRecyclerView;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.core.JumpModel;
import com.wj.dawsonwanandroid.ui.adapter.ArticleListAdapter;
import com.wj.dawsonwanandroid.ui.contract.KnowledgeContract;
import com.wj.dawsonwanandroid.ui.presenter.KnowledgePresenter;
import com.wj.dawsonwanandroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wj on 2018/5/22.
 */
public class KnowledgeFragment extends BaseFragment<KnowledgePresenter> implements KnowledgeContract.View,
        OnRefreshLoadmoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    CoreRecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private int cId;
    private List<ArticleBean> articleList;
    private ArticleListAdapter adapter;

    public static KnowledgeFragment newInstance(int cid) {
        KnowledgeFragment fragment = new KnowledgeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.Key.KEY, cid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        cId = bundle.getInt(Constants.Key.KEY);
        articleList = new ArrayList<>();

        adapter = new ArticleListAdapter(articleList,true);
        BaseUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        smartRefresh.setOnRefreshLoadmoreListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        setProgressIndicator(true);
        mPresenter.loadData(true, cId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }


    @Override
    public KnowledgePresenter createPresenter() {
        return new KnowledgePresenter();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void setListData(boolean isRefresh, BaseResponse<ArticleListBean> result) {
        ArticleListBean data = result.getData();
        if (isRefresh) articleList.clear();
        if (data != null) {
            List<ArticleBean> mData = data.datas;
            if (mData != null && mData.size() > 0) {
                articleList.addAll(mData);
            }
        }
        adapter.notifyDataSetChanged();
        Utils.refreshComplete(smartRefresh);
        setProgressIndicator(false);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mPresenter.loadData(false, cId);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPresenter.loadData(true, cId);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        JumpModel.getInstance().jumpArticleDetailActivity(getActivity(), articleList.get(position));
    }
}
