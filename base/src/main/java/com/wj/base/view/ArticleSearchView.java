package com.wj.base.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import com.wj.base.R;

/**
 * Created by wj on 2018/7/9.
 */
public class ArticleSearchView extends FrameLayout {

    ImageView ivBack;
    SearchView searchView;
    private Context mContext;

    public ArticleSearchView(@NonNull Context context) {
        this(context, null);
    }

    public ArticleSearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArticleSearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_article_searchview, this);
        ivBack = view.findViewById(R.id.iv_back);
        searchView=view.findViewById(R.id.searchView);

    }


}
