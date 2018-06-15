package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.MemberDataAdapter;
import com.tupperware.huishengyi.component.DaggerMemberDataFragmentComponent;
import com.tupperware.huishengyi.entity.member.MemberAddBean;
import com.tupperware.huishengyi.entity.member.MemberReportBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.module.MemberDataPresenterModule;
import com.tupperware.huishengyi.ui.contract.MemberDataContract;
import com.tupperware.huishengyi.ui.presenter.MemberDataPresenter;
import com.tupperware.huishengyi.utils.TabLayoutUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/6/11.
 */

public class MemberDataFragment extends BaseFragment implements MemberDataContract.View, PtrHandler {

    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;
    @BindView(R.id.refresh_ll)
    LinearLayout mRefreshLl;
    @BindView(R.id.member_attribute_tab)
    TabLayout mMemberTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private View rootview;
    private MemberDataAdapter mAdapter;
    private String storeId;

    @Inject
    MemberDataPresenter mPresenter;

    public static MemberDataFragment newInstance() {
        MemberDataFragment fragment = new MemberDataFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        storeId = (String) mSharePreDate.getParam(GlobalConfig.STORE_ID, "");
        initLayout();
        initLayoutData();
        return rootview;
    }

    @Override
    public void initLayout() {
//        mAdapter = new MemberDataAdapter(rootview.getContext(), getActivity().getSupportFragmentManager());

        DaggerMemberDataFragmentComponent.builder()
                .appComponent(getAppComponent())
                .memberDataPresenterModule(new MemberDataPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
        /**
         * Tab中的Page有嵌套Fragment,换成 getChildFragmentManager,可以解决getItem()不被调用，因为第二次进入该
         * fragemnt，getItem()不会被调用
         */
        mAdapter = new MemberDataAdapter(rootview.getContext(), getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mMemberTab.setupWithViewPager(mViewPager);
        TabLayoutUtils.getInstance().setIndicator(mMemberTab, 20,20);
    }

    @Override
    public void initLayoutData() {
        mPresenter.getMemberReportData(storeId);
        mPresenter.getTodayNewAddData(storeId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_member;
    }


    /**
     * 由于后台接口原因，将基本数据和今日新增数据分开获取，通过父fragment获得数据后刷新子fragment。
     * @param memberReportBean
     */
    @Override
    public void setMemberReportData(MemberReportBean memberReportBean) {
        int size = getChildFragmentManager().getFragments().size();
        for(int i = 0; i < size; i++) {
            MemberDataDetialFragment fragment = (MemberDataDetialFragment) getChildFragmentManager().getFragments().get(i);
            if(fragment != null) {
                fragment.refreshBaseData(memberReportBean);
            }
        }
        mAdapter.setReportData(memberReportBean);
    }

    @Override
    public void setTodayNewAddData(MemberAddBean memberAddBean) {
        int size = getChildFragmentManager().getFragments().size();
        for(int i = 0; i < size; i++) {
            MemberDataDetialFragment fragment = (MemberDataDetialFragment) getChildFragmentManager().getFragments().get(i);
            if(fragment != null) {
                fragment.refreshAddData(memberAddBean);
            }
        }
        mAdapter.setMemberAddData(memberAddBean);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getMemberReportData(storeId);
                mPresenter.getTodayNewAddData(storeId);
                frame.refreshComplete();
            }
        }, 2000);
    }
}
