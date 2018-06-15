package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.MemberZhaomuAdapter;
import com.tupperware.huishengyi.config.Constant;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by dhunter on 2018/4/3.
 */

public class MemberZhaomuFragment extends BaseFragment {

    @BindView(R.id.keyproduct_status_tab)
    TabLayout mZhaomuTab;
    @BindView(R.id.res_viewpager)
    ViewPager mViewPager;

    private View rootview;
    private MemberZhaomuAdapter mAdapter;
    private LinearLayout mRightll;


    public void setRightIconText(LinearLayout mRightIcon) {
        this.mRightll = mRightIcon;
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
        initkeyDateStatusTab();
        mRightll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

    }

    @Override
    public void initLayoutData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_key_product;  // 复用
    }

    private void initkeyDateStatusTab() {
        mAdapter = new MemberZhaomuAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mZhaomuTab.setupWithViewPager(mViewPager);
//        TabLayoutUtils.getInstance().setIndicator(mZhaomuTab);
    }

    private void chooseDate() {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog =  DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
        dialog.setTargetFragment(MemberZhaomuFragment.this, Constant.REQUEST_DATE);
        dialog.show(manager, Constant.DIALOG_DATE);
    }
}
