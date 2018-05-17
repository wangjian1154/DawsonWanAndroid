package com.wj.dawsonwanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.wj.base.base.BaseAgentWebActivity;
import com.wj.base.base.SimpleActivity;
import com.wj.base.utils.StatusBarUtil;
import com.wj.base.view.TitleBar;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.core.Constants;

import butterknife.BindView;

public class WebViewActivity extends BaseAgentWebActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;

    public static void show(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
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
        titleBar.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.findViewById(R.id.container);
    }

    @Nullable
    @Override
    protected WebChromeClient getWebChromeClient() {
        WebChromeClient mWebChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //do you work
//            Log.i("Info","onProgress:"+newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (titleBar != null) {
                    titleBar.setTitle(title);
                }
            }
        };

        return mWebChromeClient;
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
        return getIntent().getStringExtra(Constants.Key.KEY);
    }
}
