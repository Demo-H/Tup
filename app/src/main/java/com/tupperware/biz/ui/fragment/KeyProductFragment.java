package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.KeyProductAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.utils.TabLayoutUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by dhunter on 2018/3/22.
 */

public class KeyProductFragment extends BaseFragment {

    @BindView(R.id.keyproduct_status_tab)
    TabLayout mKeyProductTab;
    @BindView(R.id.res_viewpager)
    ViewPager mViewPager;

    private View rootview;
    private KeyProductAdapter mAdapter;
    private LinearLayout mRightll;

    public void setRightIconText(LinearLayout mRightIcon) {
        this.mRightll = mRightIcon;
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
        initkeyProductStatusTab();
        mRightll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_key_product;
    }

    private void initkeyProductStatusTab() {
        mAdapter = new KeyProductAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mKeyProductTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mKeyProductTab);
    }

    private void chooseDate() {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog =  DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
        dialog.setTargetFragment(KeyProductFragment.this, Constant.REQUEST_DATE);
        dialog.show(manager, Constant.DIALOG_DATE);
    }
}
