package com.tupperware.huishengyi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
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


    public static CollClassifyFragment newInstance(Bundle bundle) {
        CollClassifyFragment fragment = new CollClassifyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
        initLayoutData();
        return rootview;
    }

    @Override
    public void initLayout() {
        fragmentManager = getActivity().getSupportFragmentManager();
        mArticleFragment = new CollArticleFragment();
        mExperienceFragment = new CollExperienceFragmnet();
        mCourseFragment = new CollCourseFragment();
        fragmentManager.beginTransaction().add(R.id.contentFrame, mArticleFragment, "0").commitAllowingStateLoss();
        fragmentManager.beginTransaction().add(R.id.contentFrame, mExperienceFragment, "1").commitAllowingStateLoss();
        fragmentManager.beginTransaction().add(R.id.contentFrame, mCourseFragment, "2").commitAllowingStateLoss();
        selection(0);
    }

    @Override
    public void initLayoutData() {

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
        switch (position) {
            case 0:
                mInfoTSelected.setSelected(true);
                mQSelected.setSelected(false);
                mCourseSelected.setSelected(false);
                fragmentManager.beginTransaction().hide(mExperienceFragment).hide(mCourseFragment)
                        .show(mArticleFragment).commit();
                break;
            case 1:
                mInfoTSelected.setSelected(false);
                mQSelected.setSelected(true);
                mCourseSelected.setSelected(false);
                fragmentManager.beginTransaction().hide(mArticleFragment).hide(mCourseFragment)
                        .show(mExperienceFragment).commit();
                break;
            case 2:
                mInfoTSelected.setSelected(false);
                mQSelected.setSelected(false);
                mCourseSelected.setSelected(true);
                fragmentManager.beginTransaction().hide(mArticleFragment).hide(mExperienceFragment)
                        .show(mCourseFragment).commit();
                break;
        }
    }
}
