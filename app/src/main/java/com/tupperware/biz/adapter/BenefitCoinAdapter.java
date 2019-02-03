package com.tupperware.biz.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.ui.fragment.BenefitCoinExpenditureFragment;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinAdapter extends FragmentPagerAdapter {

    private String[] mTilte;
    private Bundle bundle;

    public BenefitCoinAdapter(Context context, FragmentManager fm) {
        super(fm);
        mTilte = context.getResources().getStringArray(R.array.benefit_coin);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilte[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        bundle = new Bundle();
        bundle.putInt(Constant.FRAGMENT_TAB_POSITION, position);
        bundle.putString(Constant.FRAGMENT_FLAG, Constant.BENEFIT);
        BaseFragment fragment = BenefitCoinExpenditureFragment.newInstance(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return Constant.MENU_TABLE_ITEM_COUNT_3;
    }
}
