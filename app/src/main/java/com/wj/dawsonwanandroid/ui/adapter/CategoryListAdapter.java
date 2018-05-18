package com.wj.dawsonwanandroid.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.AndroidTreeBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class CategoryListAdapter extends BaseQuickAdapter<AndroidTreeBean, BaseViewHolder> {

    private final LayoutInflater mInflater;
    private Context mContext;

    public CategoryListAdapter(Context mContext,@Nullable List<AndroidTreeBean> data) {
        super(R.layout.item_list_category, data);
        this.mContext=mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    protected void convert(BaseViewHolder helper, AndroidTreeBean item) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        TagFlowLayout flowLayout = helper.getView(R.id.tag_layout);
        
        final List<AndroidTreeBean.ChildrenBean> children = item.getChildren();
        tvTitle.setText(item.getName());
        flowLayout.setAdapter(new TagAdapter<AndroidTreeBean.ChildrenBean>(children) {
            @Override
            public View getView(FlowLayout parent, int position, AndroidTreeBean.ChildrenBean childrenBean) {
                TextView tvContent = (TextView) mInflater.inflate(R.layout.view_item_category_content,
                        parent, false);
                tvContent.setText(children.get(position).getName());
                return tvContent;
            }
        });
    }
}
