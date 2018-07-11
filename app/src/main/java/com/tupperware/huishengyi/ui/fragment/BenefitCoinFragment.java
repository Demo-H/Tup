package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.BenefitCoinAdapter;
import com.tupperware.huishengyi.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinFragment extends BaseFragment {

    @BindView(R.id.benefit_coin_status_tab)
    TabLayout mBenefitCoinTab;
    @BindView(R.id.res_viewpager)
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
        initBenefitCoinStatusTab();

    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_benefit_coin;
    }

    private void initBenefitCoinStatusTab() {
        BenefitCoinAdapter adapter = new BenefitCoinAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mBenefitCoinTab.setupWithViewPager(mViewPager);

    }
}
