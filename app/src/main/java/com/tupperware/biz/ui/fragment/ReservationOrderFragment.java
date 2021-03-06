package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.ReservationOrderAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.utils.TabLayoutUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/14.
 */

public class ReservationOrderFragment extends BaseFragment {

    @BindView(R.id.reservation_status_tab)
    TabLayout mReservationTab;

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
        return R.layout.fragment_reservation_order;
    }

    private void initReservationStatusTab() {

        ReservationOrderAdapter adapter = new ReservationOrderAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mReservationTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mReservationTab);

    }
}
