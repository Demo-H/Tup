package com.tupperware.biz.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.ui.fragment.MemberDataFragment;
import com.tupperware.biz.ui.fragment.SaleDataFragment;

/**
 * Created by dhunter on 2018/6/11.
 */

public class DataShowAdapter extends FragmentPagerAdapter {
    private String[] mTilte;
    private BaseFragment fragment;

    public DataShowAdapter(Context context, FragmentManager fm) {
        super(fm);
        mTilte = context.getResources().getStringArray(R.array.data_window);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilte[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        if(position == 1) {
            fragment = SaleDataFragment.newInstance();
        } else {
            fragment = MemberDataFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Constant.MENU_TABLE_ITEM_COUNT_2;
    }
}
