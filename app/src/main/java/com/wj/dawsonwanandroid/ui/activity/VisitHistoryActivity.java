package com.wj.dawsonwanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.wj.base.base.SimpleActivity;
import com.wj.base.utils.BaseUtils;
import com.wj.base.utils.ScreenUtils;
import com.wj.base.utils.ToastUtils;
import com.wj.base.view.TitleBar;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.core.DBManager;
import com.wj.dawsonwanandroid.core.JumpModel;
import com.wj.dawsonwanandroid.core.MyApp;
import com.wj.dawsonwanandroid.dao.ArticleBeanDao;
import com.wj.dawsonwanandroid.ui.adapter.ArticleListAdapter;
import com.wj.dawsonwanandroid.utils.Utils;

import org.greenrobot.greendao.DbUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class VisitHistoryActivity extends SimpleActivity implements
        OnRefreshLoadmoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.iv_to_top)
    ImageView ivToTop;
    @BindView(R.id.title_bar)
    TitleBar titleBar;

    private List<ArticleBean> articleList;
    private ArticleListAdapter adapter;
    private int height = ScreenUtils.getHeightInPx(MyApp.getInstance());
    private int overallXScroll = 0;
    private int limit = 10;
    private MaterialDialog dialogRemove;
    private MaterialDialog dialogClear;

    public static void show(Context context) {
        Intent intent = new Intent(context, VisitHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_visit_history;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {

        titleBar.setTitle("浏览记录");
        articleList = new ArrayList<>();
        setProgressIndicator(true);

        adapter = new ArticleListAdapter(articleList, false);
        BaseUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());

        smartRefresh.setOnRefreshLoadmoreListener(this);
        adapter.setOnItemClickListener(this);
        titleBar.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                overallXScroll = overallXScroll + dy;
                if (overallXScroll <= 0) {
                    ivToTop.setVisibility(View.GONE);
                } else if (overallXScroll > 0 && overallXScroll <= height) {
                    ivToTop.setVisibility(View.GONE);
                } else {
                    ivToTop.setVisibility(View.VISIBLE);

                }
            }
        });

        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {

                if (dialogRemove == null) {
                    dialogRemove = new MaterialDialog
                            .Builder(VisitHistoryActivity.this)
                            .title("提示")
                            .content("确定移除这条浏览记录？")
                            .positiveText("确定")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    removeVisit(articleList.get(position), position);
                                    dialog.dismiss();
                                }
                            })
                            .negativeText("取消")
                            .onNegative(null)
                            .show();
                } else {
                    dialogRemove.show();
                }


                return false;
            }
        });

        titleBar.setRightText("清除", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogClear==null){
                    dialogClear = new MaterialDialog
                            .Builder(VisitHistoryActivity.this)
                            .title("提示")
                            .content("确定移除所有浏览记录？")
                            .positiveText("确定")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    removeAllVisit();
                                    dialog.dismiss();
                                }
                            })
                            .negativeText("取消")
                            .onNegative(null)
                            .show();
                }else {
                    dialogClear.show();
                }
            }
        });

        loadData(true);
    }

    private void loadData(boolean isRefresh) {
        if (isRefresh) {
            articleList.clear();
        }

        articleList.addAll(findAllVisit(limit, articleList.size()));
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        Utils.refreshComplete(smartRefresh);
        setProgressIndicator(false);
    }

    /**
     * 查找浏览记录
     *
     * @return
     */
    private List<ArticleBean> findAllVisit(int limit, int offset) {
        DBManager dbManager = new DBManager();
        List<ArticleBean> list = dbManager.getDaoSession()
                .queryBuilder(ArticleBean.class).limit(limit).offset(offset).list();
        dbManager.close();

        return list;
    }

    private void removeVisit(ArticleBean articleBean, int position) {
        DBManager dbManager = new DBManager();
        dbManager.getDaoSession().delete(articleBean);
        dbManager.close();
        articleList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void removeAllVisit() {
        DBManager dbManager = new DBManager();
        dbManager.getDaoSession().getArticleBeanDao().deleteAll();
        dbManager.close();
        articleList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        JumpModel.getInstance().jumpArticleDetailActivity(this, articleList.get(position));
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        loadData(false);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        loadData(true);
    }
}
