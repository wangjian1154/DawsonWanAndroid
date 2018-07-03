package com.wj.dawsonwanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.ArticleListBean;

import java.util.List;

/**
 * Created by wj on 2018/5/15.
 */
public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    public ArticleListAdapter(@Nullable List<ArticleBean> data) {
        super(R.layout.item_list_article, data);
    }


    @Override
    protected void convert(final BaseViewHolder helper, ArticleBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvAuthor = helper.getView(R.id.tv_author);
        TextView tvTime = helper.getView(R.id.tv_time);
        ImageView ivCollection = helper.getView(R.id.iv_collection);

        tvTitle.setText(item.title);
        tvAuthor.setText("作者：" + item.author);
        tvTime.setText(item.niceDate);

        ivCollection.setImageResource(item.collect ? R.drawable.ic_collection : R.drawable.ic_collection_normal);

        helper.addOnClickListener(R.id.ll_collection);
    }

}
