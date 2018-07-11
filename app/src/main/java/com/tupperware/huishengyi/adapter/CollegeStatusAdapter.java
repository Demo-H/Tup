package com.tupperware.huishengyi.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.CollegeTabBean;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.CollClassifyFragment;

/**
 * Created by dhunter on 2018/4/17.
 */

public class CollegeStatusAdapter extends FragmentPagerAdapter {

    private String[] mTilte;
    private CollegeTabBean mData;

    public CollegeStatusAdapter(Context context, FragmentManager fm, CollegeTabBean mData) {
        super(fm);
        this.mData = mData;
        if(mData == null) {
            mTilte = context.getResources().getStringArray(R.array.college_title);
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
        bundle.putInt(Constant.LABLE_ID, mData.models.get(position).id);
        BaseFragment fragment = CollClassifyFragment.newInstance(bundle);
//        if(position == 0) {
//            fragment = CollClassifyFragment.newInstance(bundle);
//        } else {
//            fragment = CollStoreFragment.newInstance(bundle);
//        }
//        if(position == 0) {
//            fragment = CollCourseFragment.newInstance(bundle);
//        } else if(position == 1) {
//            fragment = CollDirectVideoFragment.newInstance(bundle);
//        } else if(position == 2) {
//            fragment = CollArticleFragment.newInstance(bundle);
//        } else {
//            fragment = CollExperienceFragmnet.newInstance(bundle);
//        }
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
}