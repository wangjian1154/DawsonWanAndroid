package com.wj.dawsonwanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wj.base.utils.ImageLoadUtils;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.ArticleBean;
import com.wj.dawsonwanandroid.bean.ProjectListBean;

import java.util.List;

/**
 * Created by wj on 2018/5/18.
 */
public class ProjectListAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    public ProjectListAdapter(@Nullable List<ArticleBean> data) {
        super(R.layout.item_list_project, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean item) {
        TextView tvContent = helper.getView(R.id.tv_content);
        TextView tvTitle = helper.getView(R.id.tv_title);
        TextView tvTime= helper.getView(R.id.tv_time);
        TextView tvAuthor= helper.getView(R.id.tv_author);
        RoundedImageView iv_pic = helper.getView(R.id.iv_pic);

        tvTitle.setText(item.title);
        tvContent.setText(item.desc);
        tvTime.setText(item.niceDate);
        tvAuthor.setText(item.author);
        ImageLoadUtils.display(mContext, item.envelopePic, iv_pic);
    }
}
