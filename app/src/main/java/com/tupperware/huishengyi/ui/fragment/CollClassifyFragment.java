package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/2.
 * 精选资讯，经验问答，精选课程分类
 */

public class CollClassifyFragment extends BaseFragment {

    @BindView(R.id.selected_infomation)
    TextView mInfoTSelected;
    @BindView(R.id.selected_question)
    TextView mQSelected;
    @BindView(R.id.slescted_course)
    TextView mCourseSelected;

    private int mTabPosition;
    private View rootview;
    private FragmentManager fragmentManager;
    private CollArticleFragment mArticleFragment;
    private CollExperienceFragmnet mExperienceFragment;
    private CollCourseFragment mCourseFragment;
    private BaseFragment currentFragment;
    private boolean issavedInstanceState = false;
    private int tagId;


    public static CollClassifyFragment newInstance(Bundle bundle) {
        CollClassifyFragment fragment = new CollClassifyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getChildFragmentManager();
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
            tagId = bundle.getInt(Constant.LABLE_ID);
        }
        if(savedInstanceState != null) {
            issavedInstanceState = true;
            mArticleFragment = (CollArticleFragment) fragmentManager.findFragmentByTag(CollArticleFragment.class.getName());
            mExperienceFragment = (CollExperienceFragmnet) fragmentManager.findFragmentByTag(CollExperienceFragmnet.class.getName());
            mCourseFragment = (CollCourseFragment) fragmentManager.findFragmentByTag(CollCourseFragment.class.getName());
            fragmentManager.beginTransaction().show(mArticleFragment).hide(mExperienceFragment).hide(mCourseFragment);
            //把当前显示的fragment记录下来
            if(mInfoTSelected != null) {
                mInfoTSelected.setSelected(true);
            }
            currentFragment = mArticleFragment;
        } else {
            issavedInstanceState = false;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
        requestData();
        return rootview;
    }

    @Override
    public void initLayout() {
        if(!issavedInstanceState) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.LABLE_ID, tagId);
            mArticleFragment = CollArticleFragment.newInstance(bundle);
            mExperienceFragment = CollExperienceFragmnet.newInstance(bundle);
            mCourseFragment = CollCourseFragment.newInstance(bundle);
            selection(0);
        }
//        fragmentManager = getActivity().getSupportFragmentManager();
//        mArticleFragment = new CollArticleFragment();
//        mExperienceFragment = new CollExperienceFragmnet();
//        mCourseFragment = new CollCourseFragment();
//        fragmentManager.beginTransaction().add(R.id.contentFrame, mArticleFragment, "0").commitAllowingStateLoss();
//        fragmentManager.beginTransaction().add(R.id.contentFrame, mExperienceFragment, "1").commitAllowingStateLoss();
//        fragmentManager.beginTransaction().add(R.id.contentFrame, mCourseFragment, "2").commitAllowingStateLoss();
//        selection(0);
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classify_coll;
    }

    @OnClick({R.id.selected_infomation, R.id.selected_question, R.id.slescted_course})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selected_infomation:
                selection(0);
                break;
            case R.id.selected_question:
                selection(1);
                break;
            case R.id.slescted_course:
                selection(2);
                break;
        }
    }

    private void selection(int position) {
        if(mInfoTSelected == null || mQSelected == null || mCourseSelected == null) {
            showFragment(mArticleFragment);
            return;
        }
        switch (position) {
            case 0:
                mInfoTSelected.setSelected(true);
                mQSelected.setSelected(false);
                mCourseSelected.setSelected(false);
                showFragment(mArticleFragment);
//                fragmentManager.beginTransaction().hide(mExperienceFragment).hide(mCourseFragment)
//                        .show(mArticleFragment).commit();
                break;
            case 1:
                mInfoTSelected.setSelected(false);
                mQSelected.setSelected(true);
                mCourseSelected.setSelected(false);
                showFragment(mExperienceFragment);
//                fragmentManager.beginTransaction().hide(mArticleFragment).hide(mCourseFragment)
//                        .show(mExperienceFragment).commit();
                break;
            case 2:
                mInfoTSelected.setSelected(false);
                mQSelected.setSelected(false);
                mCourseSelected.setSelected(true);
                showFragment(mCourseFragment);
//                fragmentManager.beginTransaction().hide(mArticleFragment).hide(mExperienceFragment)
//                        .show(mCourseFragment).commit();
                break;
        }
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(BaseFragment fg){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //如果之前没有添加过
        if(!fg.isAdded()){
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .add(R.id.contentFrame, fg, fg.getClass().getName());  //第三个参数为添加当前的fragment时绑定一个tag，即绑定fragment的类名
            } else {
                transaction.add(R.id.contentFrame, fg, fg.getClass().getName());
            }
        }else{
            if(currentFragment != null) {
                transaction
                        .hide(currentFragment)
                        .show(fg);
            } else {
                transaction.show(fg);
            }
        }
        currentFragment = fg;
        transaction.commit();
    }
}
