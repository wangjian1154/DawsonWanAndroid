package com.wj.dawsonwanandroid.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.wj.base.base.SimpleActivity;
import com.wj.dawsonwanandroid.R;

import butterknife.BindView;

public class WebViewActivity extends SimpleActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        
    }
}
