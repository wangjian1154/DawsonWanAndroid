package com.wj.dawsonwanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wj.base.base.BaseAgentWebActivity;
import com.wj.base.utils.StatusBarUtil;
import com.wj.base.view.CoreTitleView;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.utils.ShareUtils;

import butterknife.BindView;

public class ArticleDetailViewActivity extends BaseAgentWebActivity {

    @BindView(R.id.title_bar)
    CoreTitleView titleBar;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;

    private String url;

    public static void show(Context context, String url) {
        Intent intent = new Intent(context, ArticleDetailViewActivity.class);
        intent.putExtra(Constants.Key.KEY, url);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.title_bar_bg));
        titleBar.setMarquee();

        titleBar.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mWebView.reload();
                smartRefresh.finishRefresh();
            }
        });
        titleBar.setRightImgButton(R.drawable.ic_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeb(ArticleDetailViewActivity.this, mTitleStr, null, null, url);
            }
        });

        setOnLoadWebViewCallback(new LoadWebViewCallback() {
            @Override
            public void onProgressChanged(int progress) {
                if (progress == 100) {
                    titleBar.getRightButton().setVisibility(View.VISIBLE);
                } else {
                    titleBar.getRightButton().setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onTitleLoadComplete(String title) {
                titleBar.setTitle(title);
            }
        });
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.findViewById(R.id.container);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getIndicatorColor() {
        return getResources().getColor(R.color.webview_load_indicator);
    }


    @Override
    protected int getIndicatorHeight() {
        return 3;
    }

    @Nullable
    @Override
    protected String getUrl() {
        url = getIntent().getStringExtra(Constants.Key.KEY);
        return url;
    }

}
