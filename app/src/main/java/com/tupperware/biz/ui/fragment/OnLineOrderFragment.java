package com.tupperware.biz.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.OnlineOrderAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.ui.activities.SearchActivity;
import com.tupperware.biz.utils.TabLayoutUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/14.
 */

public class OnLineOrderFragment extends BaseFragment {

    @BindView(R.id.order_status_tab)
    TabLayout mOrderStatusTab;

    @BindView(R.id.online_viewpager)
    ViewPager mViewPager;

    private View rootview;

    public static OnLineOrderFragment newInstance() {
        OnLineOrderFragment fragment = new OnLineOrderFragment();
        return fragment;
    }

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
//        initOrderTypeTab();
    }

    @Override
    public void requestData() {
        isViewCreated = true;
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        if (isViewCreated) {
            initOrderTypeTab();
        }
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

    @OnClick({R.id.right_search_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_search_icon:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.ORDER_FRAGMENT);
                startActivity(intent);
                break;
        }
    }

}
