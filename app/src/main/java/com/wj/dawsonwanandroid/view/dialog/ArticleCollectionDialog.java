package com.wj.dawsonwanandroid.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;

import com.wj.base.base.SimpleDialogFragment;
import com.wj.dawsonwanandroid.R;

import butterknife.BindView;

/**
 * Created by wj on 2018/5/16.
 */
public class ArticleCollectionDialog extends SimpleDialogFragment {

    @BindView(R.id.iv_like)
    ImageView ivLike;

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {

        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_style_fill_width2);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_article_collection;
    }

}
