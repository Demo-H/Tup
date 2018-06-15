package com.tupperware.huishengyi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.ui.fragment.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.DataSingleProductTopFragment;
import com.tupperware.huishengyi.ui.fragment.DataSingleVipPrecentFragment;
import com.tupperware.huishengyi.ui.fragment.DataSingleVipStatusFragment;
import com.tupperware.huishengyi.ui.fragment.DataStoreSaleMoneyFragment;

/**
 * Created by dhunter on 2018/4/10.
 */

public class DataWindowChartAdapter extends FragmentPagerAdapter {
    private String[] mTilte;
    private Bundle bundle;
    private String mTitleFlag;

    public DataWindowChartAdapter(Context context, FragmentManager fm, String mTitleFlag) {
        super(fm);
        mTilte = context.getResources().getStringArray(R.array.month);
        this.mTitleFlag = mTitleFlag;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilte[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        BaseFragment fragment = null;
        if(Constant.SINGLE_VIP_COUNT_PRECENT.equals(mTitleFlag) || Constant.SINGLE_SALE_ATTRIBUTE.equals(mTitleFlag) ||
                Constant.STORE_VIP_SALE_PRECENT.equals(mTitleFlag) || Constant.STORE_SALE_ATTRIBUTE.equals(mTitleFlag)) {
            bundle = new Bundle();
            bundle.putInt(Constant.FRAGMENT_TAB_POSITION, position);
            bundle.putString(Constant.ACTIVITY_TITLE, mTitleFlag);
            fragment = DataSingleVipPrecentFragment.newInstance(bundle);
        } else if(Constant.SINGLE_VIP_STATUS.equals(mTitleFlag) || Constant.STORE_SALE_ANALYSIS.equals(mTitleFlag) ||
        Constant.STORE_VIP_STATUS_ANALYSIS.equals(mTitleFlag)) {
            bundle = new Bundle();
            bundle.putInt(Constant.FRAGMENT_TAB_POSITION, position);
            bundle.putString(Constant.ACTIVITY_TITLE, mTitleFlag);
            fragment = DataSingleVipStatusFragment.newInstance(bundle);
        } else if(Constant.SINGLE_PRODUCT_TOP.equals(mTitleFlag) || Constant.SINGLE_PRODUCT_SALE_TOP.equals(mTitleFlag) ||
                Constant.SINGLE_MAIN_PRODUCT.equals(mTitleFlag) || Constant.PRODUCT_SALE_TOP.equals(mTitleFlag) ||
                Constant.PRODUCT_SALE_MONEY_TOP.equals(mTitleFlag)) {
            bundle = new Bundle();
            bundle.putInt(Constant.FRAGMENT_TAB_POSITION, position);
            bundle.putString(Constant.ACTIVITY_TITLE, mTitleFlag);
            fragment = DataSingleProductTopFragment.newInstance(bundle);
        } else if(Constant.STORE_SALE_MONEY_LIST.equals(mTitleFlag) || Constant.STORE_SALE_LIST.equals(mTitleFlag) ||
                Constant.MAIN_PRODUCT_LIST.equals(mTitleFlag)) {
            bundle = new Bundle();
            bundle.putInt(Constant.FRAGMENT_TAB_POSITION, position);
            bundle.putString(Constant.ACTIVITY_TITLE, mTitleFlag);
            fragment = DataStoreSaleMoneyFragment.newInstance(bundle);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return Constant.MENU_TABLE_ITEM_COUNT_3;
    }
}