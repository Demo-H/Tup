package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.CouponVipAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.utils.TabLayoutUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/21.
 */

public class CouponVipFragment extends BaseFragment {

    @BindView(R.id.coupon_status_tab)
    TabLayout mCouponTab;
    @BindView(R.id.res_viewpager)
    ViewPager mViewPager;

    private View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
//        requestData();
        return rootview;
    }

    @Override
    public void initLayout() {
        initReservationStatusTab();

    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon_vip;
    }

    private void initReservationStatusTab() {
        CouponVipAdapter adapter = new CouponVipAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mCouponTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mCouponTab, 20,20);
    }
}
