package com.wj.dawsonwanandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioButton;

import com.wj.base.base.BaseFragmentPageAdapter;
import com.wj.base.base.SimpleActivity;
import com.wj.base.view.NoScrollViewPager;
import com.wj.dawsonwanandroid.R;
import com.wj.dawsonwanandroid.ui.fragment.CategoryFragment;
import com.wj.dawsonwanandroid.ui.fragment.HomeFragment;
import com.wj.dawsonwanandroid.ui.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends SimpleActivity {

    @BindView(R.id.vp_main)
    NoScrollViewPager viewPager;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_category)
    RadioButton rbCategory;
    @BindView(R.id.rb_person)
    RadioButton rbPerson;

    private List<Fragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewAndEvent(Bundle savedInstanceState) {

        initData();

        BaseFragmentPageAdapter fragmentAdapter = new BaseFragmentPageAdapter(
                getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        switchFragment(0);
        viewPager.setOffscreenPageLimit(fragments.size());

    }

    private void initData() {
        fragments = new ArrayList<>();

        fragments.add(new HomeFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new MineFragment());
    }

    @OnClick({R.id.rb_home, R.id.rb_category, R.id.rb_person})
    public void onBottomNavigation(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                switchFragment(0);
                break;

            case R.id.rb_category:
                switchFragment(1);
                break;

            case R.id.rb_person:
                switchFragment(2);
                break;
        }
    }

    private void switchFragment(int position) {
        viewPager.setCurrentItem(position);
        rbHome.setChecked(false);
        rbCategory.setChecked(false);
        rbPerson.setChecked(false);
        switch (position) {
            case 0:
                rbHome.setChecked(true);
                break;

            case 1:
                rbCategory.setChecked(true);
                break;

            case 2:
                rbPerson.setChecked(true);
                break;
        }
    }

}
