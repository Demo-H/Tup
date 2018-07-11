package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.OnlineOrderAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.utils.TabLayoutUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/14.
 */

public class OnLineOrderFragment extends BaseFragment {

    @BindView(R.id.order_status_tab)
    TabLayout mOrderStatusTab;

    @BindView(R.id.online_viewpager)
    ViewPager mViewPager;

    private View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
        requestData();
        return rootview;
    }

    @Override
    public void initLayout() {
        initOrderTypeTab();
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_online_order;
    }

    private void initOrderTypeTab() {

        OnlineOrderAdapter adapter = new OnlineOrderAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mOrderStatusTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mOrderStatusTab);

    }

}
