package com.wj.dawsonwanandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wj.base.base.BaseFragmentPageAdapter;
import com.wj.base.base.SimpleActivity;
import com.wj.base.utils.StatusBarUtil;
import com.wj.base.view.CoreTitleView;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.bean.AndroidTreeBean;
import com.wj.dawsonwanandroid.core.Constants;
import com.wj.dawsonwanandroid.ui.adapter.MyCommonNavigatorAdapter;
import com.wj.dawsonwanandroid.ui.fragment.KnowledgeFragment;
import com.wj.dawsonwanandroid.utils.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KnowledgeActivity extends SimpleActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.title_bar)
    CoreTitleView titleBar;

    private List<Fragment> fragments;
    private List<String> titles;
    private CommonNavigator commonNavigator;
    private AndroidTreeBean treeBean;

    public static void show(Context context, AndroidTreeBean bean) {
        Intent intent = new Intent(context, KnowledgeActivity.class);
        intent.putExtra(Constants.Key.KEY, bean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        StatusBarUtil.setColor(this, Utils.getResourcesColor(this, R.color.title_bar_bg));
        return R.layout.activity_knowledge;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {
        treeBean = (AndroidTreeBean) getIntent().getSerializableExtra(Constants.Key.KEY);
        titleBar.setTitle(treeBean.getName());

        fragments = new ArrayList<>();
        titles = new ArrayList<>();

        magicIndicator.setBackgroundColor(Color.WHITE);
        commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);

        for (AndroidTreeBean.ChildrenBean bean : treeBean.getChildren()) {
            titles.add(bean.getName());
            fragments.add(KnowledgeFragment.newInstance(bean.getId()));
        }

        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        MyCommonNavigatorAdapter adapter = new MyCommonNavigatorAdapter(titles, viewPager);
        BaseFragmentPageAdapter pageAdapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(pageAdapter);
        commonNavigator.setAdapter(adapter);

        titleBar.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
