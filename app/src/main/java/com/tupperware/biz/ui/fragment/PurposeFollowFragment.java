package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tupperware.biz.R;
import com.tupperware.biz.adapter.PurposeFollowAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.utils.TabLayoutUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/3/30.
 */

public class PurposeFollowFragment extends BaseFragment {

    @BindView(R.id.keyproduct_status_tab)
    TabLayout mPurFollowTab;
    @BindView(R.id.res_viewpager)
    ViewPager mViewPager;

    private View rootview;
    private PurposeFollowAdapter mAdapter;
//    private LinearLayout mRightll;

//    public void setRightIconSearch(LinearLayout mRightIcon) {
//        this.mRightll = mRightIcon;
//    }

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
        initpurFollowStatusTab();
//        mRightll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                search();
//            }
//        });
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_key_product; //复用布局
    }

    private void initpurFollowStatusTab() {
        mAdapter = new PurposeFollowAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mPurFollowTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mPurFollowTab);
    }

//    private void search() {
//        toast("search");
//    }

}
