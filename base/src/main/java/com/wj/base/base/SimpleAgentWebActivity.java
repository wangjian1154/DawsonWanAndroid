package com.wj.base.base;

import android.os.Bundle;

import com.just.agentweb.AgentWeb;

/**
 * Created by wj on 2018/5/16.
 */
public  class SimpleAgentWebActivity extends SimpleActivity {

    protected AgentWeb mAgentWeb;


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(getUrl());

    }
}
