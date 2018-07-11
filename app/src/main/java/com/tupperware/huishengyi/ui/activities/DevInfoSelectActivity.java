package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.tablayout.SegmentTabLayout;
import com.android.dhunter.common.widget.tablayout.listener.OnTabSelectListener;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.DevInfoSelectAdapter;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/28.
 */

public class DevInfoSelectActivity extends BaseActivity {

    private static final String TAG = "DevInfoSelectActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.dev_info_image)
    ImageView mInfoImage;
    @BindView(R.id.dev_info_tab)
    SegmentTabLayout mTab;
    @BindView(R.id.viewpage)
    ViewPager mViewPager;
    @BindView(R.id.next_step)
    TextView mNextView;

    private DevInfoSelectAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_info_select;
    }

    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        mTitle.setText(getResources().getString(R.string.member_info));
        mRightNext.setVisibility(View.GONE);
        initTab();
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.left_back, R.id.next_step})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next_step:
                Intent intent = new Intent(this, DevInfoResultActivity.class);
                startActivity(intent);
                break;
        }
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }

    private void initTab() {
        mAdapter = new DevInfoSelectAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTab.setTabData(getResources().getStringArray(R.array.dev_info));
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTab.setCurrentTab(0);
    }



}
