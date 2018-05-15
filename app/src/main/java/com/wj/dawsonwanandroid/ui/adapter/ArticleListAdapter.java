package com.wj.dawsonwanandroid.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wj.dawsonwanandroid.bean.ArticleBean;

import java.util.List;

/**
 * Created by wj on 2018/5/15.
 */
public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    public ArticleListAdapter(int layoutResId, @Nullable List<ArticleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean item) {

    }
}
