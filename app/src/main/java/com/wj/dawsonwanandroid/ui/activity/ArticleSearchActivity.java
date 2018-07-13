package com.wj.dawsonwanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.just.agentweb.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wj.base.base.BaseActivity;
import com.wj.base.base.SimpleActivity;
import com.wj.base.utils.BaseUtils;
import com.wj.base.utils.StringUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.base.view.ClearEditText;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.ArticleListBean;
import com.wj.dawsonwanandroid.bean.BaseResponse;
import com.wj.dawsonwanandroid.ui.adapter.ArticleListAdapter;
import com.wj.dawsonwanandroid.ui.contract.SearchArticleContract;
import com.wj.dawsonwanandroid.ui.presenter.SearchArticlePresenter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleSearchActivity extends BaseActivity<SearchArticlePresenter> implements SearchArticleContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int page = 0;
    private List<ArticleBean> articleList;
    private ArticleListAdapter adapter;

    public static void show(Context context) {
        Intent intent = new Intent(context, ArticleSearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_search;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        articleList = new ArrayList<>();
        BaseUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
        adapter = new ArticleListAdapter(articleList, false);
        recyclerView.setAdapter(adapter);

        smartRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadData(false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData(true);
            }
        });

    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_search:
                loadData(true);
                break;
        }
    }

    private void loadData(boolean isRefresh) {
        String searchContent = etSearch.getText().toString().trim();
        if (!StringUtils.isEmpty(searchContent)) {
            mPresenter.search(isRefresh, searchContent);
        }
    }


    @Override
    public SearchArticlePresenter createPresenter() {
        return new SearchArticlePresenter();
    }

    @Override
    public void onSearchResult(boolean isRefresh, BaseResponse<ArticleListBean> result) {

        if (isRefresh) {
            articleList.clear();
        }

        if (result != null && result.getData() != null && result.getData().size > 0
                && result.getData().datas != null && result.getData().datas.size() > 0) {
            articleList.addAll(result.getData().datas);
        }

        adapter.notifyItemRangeChanged(0, articleList.size());
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showShort(msg);
    }
}
