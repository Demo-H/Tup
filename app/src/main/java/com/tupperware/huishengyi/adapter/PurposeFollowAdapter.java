package com.tupperware.huishengyi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.PurFollowDetialFragment;

/**
 * Created by dhunter on 2018/3/30.
 */

public class PurposeFollowAdapter extends FragmentPagerAdapter {
    private String[] mTilte;
    private Bundle bundle;

    public PurposeFollowAdapter(Context context, FragmentManager fm) {
        super(fm);
        mTilte = context.getResources().getStringArray(R.array.purpose_follow_member);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilte[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        bundle = new Bundle();
        bundle.putInt(Constant.FRAGMENT_TAB_POSITION, position);
        BaseFragment fragment = PurFollowDetialFragment.newInstance(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return Constant.MENU_TABLE_ITEM_COUNT_4;
    }
}
