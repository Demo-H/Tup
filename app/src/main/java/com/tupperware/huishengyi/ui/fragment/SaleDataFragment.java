package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.SaleDataAdapter;
import com.tupperware.huishengyi.utils.TabLayoutUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/6/11.
 */

public class SaleDataFragment extends BaseFragment {

    @BindView(R.id.date_status_tab)
    TabLayout mDateTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private SaleDataAdapter mAdapter;
    private View rootview;

    public static SaleDataFragment newInstance() {
        SaleDataFragment fragment = new SaleDataFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
        initLayoutData();
        return rootview;
    }

    @Override
    public void initLayout() {
//        mAdapter = new SaleDataAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());
        /**
         * Tab中的Page有嵌套Fragment,换成 getChildFragmentManager,可以解决getItem()不被调用，因为第二次进入该
         * fragemnt，getItem()不会被调用
         */
        mAdapter = new SaleDataAdapter(rootview.getContext(), getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mDateTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mDateTab, 20,20);
    }

    @Override
    public void initLayoutData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_sale;
    }
}
