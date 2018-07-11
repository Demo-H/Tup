package com.tupperware.huishengyi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.DevInfoSelectFragment;

/**
 * Created by dhunter on 2018/3/28.
 */

public class DevInfoSelectAdapter extends FragmentPagerAdapter {

    private String[] mTilte;

    public DevInfoSelectAdapter(Context context, FragmentManager fm) {
        super(fm);
        mTilte = context.getResources().getStringArray(R.array.dev_info);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilte[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        BaseFragment fragment = new DevInfoSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return Constant.MENU_TABLE_ITEM_COUNT_5;
    }


}
