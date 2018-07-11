package com.tupperware.huishengyi.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.CourseBean;
import com.tupperware.huishengyi.listener.ICourseVideoListener;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.ui.fragment.CourseContentListFragment;
import com.tupperware.huishengyi.ui.fragment.CourseIntroduceFragment;

import java.util.ArrayList;

/**
 * Created by dhunter on 2018/4/18.
 */

public class CourseDetialAdapter extends FragmentPagerAdapter {

    private String[] mTilte;
    private ArrayList<BaseFragment> fragmentList = new ArrayList<BaseFragment>();
//    private BaseFragment fragment;
    private ICourseVideoListener mListener;

    public CourseDetialAdapter(Context context, FragmentManager fm, ICourseVideoListener mListener) {
        super(fm);
        mTilte = context.getResources().getStringArray(R.array.course_title);
        this.mListener = mListener;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilte[position];
    }

    @Override
    public BaseFragment getItem(int position) {
        if(position == 0) {
            CourseContentListFragment fragment = CourseContentListFragment.newInstance();
            fragment.setListener(mListener);
            fragmentList.add(fragment);
            return fragment;
        } else {
            CourseIntroduceFragment fragment = CourseIntroduceFragment.newInstance();
            fragmentList.add(fragment);
            return fragment;
        }
    }


    public void setDatas(CourseBean courseBean) {
        if(fragmentList != null && fragmentList.size() >=2) {
            fragmentList.get(0).refreshUI(courseBean);
            fragmentList.get(1).refreshUI(courseBean);
        }
    }


    @Override
    public int getCount() {
        return Constant.MENU_TABLE_ITEM_COUNT_2;
    }




}