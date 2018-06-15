package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.CollegeStatusAdapter;
import com.tupperware.huishengyi.component.DaggerBusinessCollegeFragmentComponent;
import com.tupperware.huishengyi.entity.college.CollegeTabBean;
import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.module.BusinessCollegePresenterModule;
import com.tupperware.huishengyi.ui.contract.BusinessCollegeContract;
import com.tupperware.huishengyi.ui.presenter.BusinessCollegePresenter;
import com.tupperware.huishengyi.utils.TabLayoutUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/16.
 * 商学院
 */

public class BusinessCollegeFragment extends BaseFragment implements BusinessCollegeContract.View {

    @BindView(R.id.success_layout)
    LinearLayout mSuccessLl;
    @BindView(R.id.error_layout)
    RelativeLayout mErrorRl;
    @BindView(R.id.college_status_tab)
    TabLayout mStatusTab;
    @BindView(R.id.college_viewpager)
    ViewPager mViewPager;

    private View rootview;
    private CollegeStatusAdapter adapter;

    @Inject
    BusinessCollegePresenter mPresenter;

    public static BusinessCollegeFragment newInstance() {
        BusinessCollegeFragment fragment = new BusinessCollegeFragment();
        return fragment;
    }

    @Nullable
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
        DaggerBusinessCollegeFragmentComponent.builder()
                .appComponent(getAppComponent())
                .businessCollegePresenterModule(new BusinessCollegePresenterModule(this, CollegeDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
    }

    @Override
    public void initLayoutData() {
        mPresenter.getLableData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_business_college;
    }

    private void initTab(CollegeTabBean mBean) {
        adapter = new CollegeStatusAdapter(rootview.getContext(), getActivity().getSupportFragmentManager(), mBean);
        mViewPager.setAdapter(adapter);
        mStatusTab.setupWithViewPager(mViewPager);
        if(mBean != null && mBean.models!=null && mBean.models.size() > 5) {
            mStatusTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mStatusTab.setTabMode(TabLayout.MODE_FIXED);
        }
        TabLayoutUtils.getInstance().setIndicator(mStatusTab);
    }

    @Override
    public void setLableData(CollegeTabBean mBean) {
        if(mBean!= null && mBean.models != null && mBean.models.size() > 0) {
            initTab(mBean);
        }
    }

    @Override
    public void setNetErrorView() {
        mSuccessLl.setVisibility(View.GONE);
        mErrorRl.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNormalView() {
        mSuccessLl.setVisibility(View.VISIBLE);
        mErrorRl.setVisibility(View.GONE);
    }

    @OnClick({R.id.error_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.error_layout:
                mPresenter.getLableData();
                break;
        }
    }
}
