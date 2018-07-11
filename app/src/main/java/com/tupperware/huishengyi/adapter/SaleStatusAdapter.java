package com.tupperware.huishengyi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.saleenter.SaleTabBean;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.SaleEnterClassifyFragment;

/**
 * Created by dhunter on 2018/5/25.
 */

public class SaleStatusAdapter extends FragmentPagerAdapter {

    private String[] mTilte;
    private SaleTabBean mData;
    private TextView mTextView;
    private String selectDate;

    public SaleStatusAdapter(Context context, FragmentManager fm, SaleTabBean mData, String selectDate) {
        super(fm);
        this.mData = mData;
        this.selectDate = selectDate;
        if(mData == null) {
            mTilte = context.getResources().getStringArray(R.array.sale_title);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mData == null) {
            return mTilte[position];
        } else {
            return mData.models.get(position).name;
        }
    }

    @Override
    public BaseFragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.FRAGMENT_TAB_POSITION, position);
        bundle.putString(Constant.SELECT_DATE, selectDate);
        SaleEnterClassifyFragment fragment = SaleEnterClassifyFragment.newInstance(bundle);
        fragment.setRefreshView(mTextView);
        return fragment;
    }

    @Override
    public int getCount() {
        if(mData == null) {
            return Constant.MENU_TABLE_ITEM_COUNT_4;
        } else {
            return mData.models.size();
        }
    }

    public void setRefreshView(TextView mTextView) {
        this.mTextView = mTextView;
    }
}
