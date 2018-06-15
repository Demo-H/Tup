package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.ServerManagerAdapter;
import com.tupperware.huishengyi.utils.TabLayoutUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/30.
 */

public class ServerManagerFragment extends BaseFragment {

    @BindView(R.id.keyproduct_status_tab)
    TabLayout mSerManagerTab;
    @BindView(R.id.res_viewpager)
    ViewPager mViewPager;

    private View rootview;
    private ServerManagerAdapter mAdapter;
    private LinearLayout mRightll;

    public void setRightIconSearch(LinearLayout mRightIcon) {
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
        initServerManagerStatusTab();
        mRightll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    @Override
    public void initLayoutData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_key_product; //复用布局
    }

    private void initServerManagerStatusTab() {
        mAdapter = new ServerManagerAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mSerManagerTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mSerManagerTab);
    }

    private void search() {
        toast("search");
    }

}