package com.tupperware.biz.ui.activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.listener.IHotSaleSysRefreshListener;
import com.tupperware.biz.ui.fragment.HotSaleEnterFragment;
import com.tupperware.biz.ui.fragment.InventoryFragment;
import com.tupperware.biz.utils.TabLayoutUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/27.
 */

public class HotSaleActivity extends BaseActivity {
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.right_image)
    ImageView mRightImage;
    @BindView(R.id.status_tab)
    TabLayout mTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private HotSaleAdapter mAdapter;
    private List<BaseFragment> fragments;
    private InventoryFragment mInventoryFragment;
    private HotSaleEnterFragment mHotSaleEnterFragment;
    private BaseFragment currentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_sale;
    }

    @Override
    protected void initLayout() {
        initToolbar();
        initData();
        mAdapter = new HotSaleAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mTab, 20, 20);
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    mRightText.setVisibility(View.GONE);
                    mRightImage.setVisibility(View.VISIBLE);
                    mRightImage.setImageResource(R.mipmap.date_btn);
                    currentFragment = mInventoryFragment;
                } else {
                    mRightText.setVisibility(View.VISIBLE);
                    mRightImage.setVisibility(View.GONE);
                    mRightText.setText(getResources().getString(R.string.save));
                    currentFragment = mHotSaleEnterFragment;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mHotSaleEnterFragment.setmListener(new IHotSaleSysRefreshListener() {
            @Override
            public void refreshData() {
//                mTab.getTabAt(0).select();
                if(mInventoryFragment != null) {
                    mInventoryFragment.requestData();
                }
            }
        });
    }

    @Override
    protected void requestData() {

    }

    private void initToolbar() {
        mRightText.setVisibility(View.GONE);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(R.mipmap.date_btn);
        mTitle.setText(getResources().getString(R.string.hot_sale));
    }

    private void initData() {
        fragments = new ArrayList<>();
        mInventoryFragment = new InventoryFragment();
        mHotSaleEnterFragment = new HotSaleEnterFragment();
        fragments.add(mInventoryFragment);
        fragments.add(mHotSaleEnterFragment);
        currentFragment = mInventoryFragment;
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                ((InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        HotSaleActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                finish();
                break;
            case R.id.next:
                if(currentFragment == mInventoryFragment) {
                    mInventoryFragment.clickRightNext();
                } else {
                    mHotSaleEnterFragment.clickRightNext();
                }
                break;
        }
    }

    public class HotSaleAdapter extends FragmentPagerAdapter {
        private String[] mTilte;
//        private Bundle bundle;

        public HotSaleAdapter(Context context, FragmentManager fm) {
            super(fm);
            mTilte = context.getResources().getStringArray(R.array.hot_sale);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTilte[position];
        }

        @Override
        public BaseFragment getItem(int position) {
//            BaseFragment fragment;
//            bundle = new Bundle();
//            if(position == 0) {
//                fragment = InventoryFragment.newInstance(bundle);
//                mRightText.setVisibility(View.GONE);
//                mRightImage.setVisibility(View.VISIBLE);
//                mRightImage.setImageResource(R.mipmap.date_btn);
//            } else {
//                fragment = HotSaleEnterFragment.newInstance(bundle);
//                mRightText.setVisibility(View.VISIBLE);
//                mRightImage.setVisibility(View.GONE);
//                mRightText.setText(getResources().getString(R.string.save));
//            }
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return Constant.MENU_TABLE_ITEM_COUNT_2;
        }
    }
}
