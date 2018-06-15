package com.tupperware.huishengyi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.ui.fragment.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.ResOrderServerFragment;

/**
 * Created by dhunter on 2018/3/14.
 */

public class ReservationOrderAdapter extends FragmentPagerAdapter {
    private String[] mTilte;

    public ReservationOrderAdapter(Context context, FragmentManager fm) {
        super(fm);
        mTilte = context.getResources().getStringArray(R.array.reservation_order_title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilte[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.FRAGMENT_TAB_POSITION, position);
        BaseFragment fragment = ResOrderServerFragment.newInstance(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
