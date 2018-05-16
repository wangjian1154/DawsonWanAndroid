package com.wj.dawsonwanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;

import java.util.List;

/**
 * Created by wj on 2018/5/15.
 */
public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean.DatasBean, BaseViewHolder> {

    public ArticleListAdapter(@Nullable List<ArticleBean.DatasBean> data) {
        super(R.layout.item_list_article, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean.DatasBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvAuthor = helper.getView(R.id.tv_author);
        TextView tvTime = helper.getView(R.id.tv_time);

        tvTitle.setText(item.title);
        tvAuthor.setText("作者：" + item.author);
        tvTime.setText(item.niceDate);

    }
}
