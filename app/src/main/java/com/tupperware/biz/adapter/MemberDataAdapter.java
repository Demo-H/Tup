package com.tupperware.biz.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.MemberAddBean;
import com.tupperware.biz.entity.member.MemberReportBean;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.ui.fragment.MemberDataDetialFragment;

/**
 * Created by dhunter on 2018/6/11.
 */

public class MemberDataAdapter extends FragmentPagerAdapter {
    private String[] mTilte;
    private MemberReportBean memberReportBean;
    private MemberAddBean memberAddBean;

    public MemberDataAdapter(Context context, FragmentManager fm) {
        super(fm);
        mTilte = context.getResources().getStringArray(R.array.member_attribute);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilte[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.FRAGMENT_TAB_POSITION, position);
        bundle.putSerializable(Constant.MEMBER_REPORT_DATA, memberReportBean);
        bundle.putSerializable(Constant.MEMBER_ADD_NEW_DATA, memberAddBean);
        BaseFragment fragment = MemberDataDetialFragment.newInstance(bundle);
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MemberDataDetialFragment fragment = (MemberDataDetialFragment)super.instantiateItem(container, position);
        if(memberReportBean != null || memberAddBean != null ) {
//            fragment.refreshData(memberReportBean);
            fragment.updateArguments(position, memberReportBean, memberAddBean);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Constant.MENU_TABLE_ITEM_COUNT_2;
    }

    public void setReportData(MemberReportBean memberReportBean) {
        this.memberReportBean = memberReportBean;
    }
    public void setMemberAddData(MemberAddBean memberAddBean) {
        this.memberAddBean = memberAddBean;
    }
}