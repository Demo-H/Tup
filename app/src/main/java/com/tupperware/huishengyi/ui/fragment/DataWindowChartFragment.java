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
import com.tupperware.huishengyi.adapter.DataWindowChartAdapter;
import com.tupperware.huishengyi.config.Constant;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/10.
 */

public class DataWindowChartFragment extends BaseFragment {

    @BindView(R.id.keyproduct_status_tab)
    TabLayout mDataWindowTab;
    @BindView(R.id.res_viewpager)
    ViewPager mViewPager;

    private View rootview;
    private DataWindowChartAdapter mAdapter;
    private LinearLayout mRightll;
    private String mTitleFlag;

    public static DataWindowChartFragment newInstance(Bundle bundle) {
        DataWindowChartFragment fragment = new DataWindowChartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitleFlag = bundle.getString(Constant.ACTIVITY_TITLE);
        }
    }


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
        mAdapter = new DataWindowChartAdapter(rootview.getContext(), getActivity().getSupportFragmentManager(), mTitleFlag);
        mViewPager.setAdapter(mAdapter);
        mDataWindowTab.setupWithViewPager(mViewPager);
    }

    private void chooseDate() {
        FragmentManager manager = getFragmentManager();
        DatePickerFragment dialog =  DatePickerFragment.newInstance(new Date(System.currentTimeMillis()));
        dialog.setTargetFragment(DataWindowChartFragment.this, Constant.REQUEST_DATE);
        dialog.show(manager, Constant.DIALOG_DATE);
    }
}