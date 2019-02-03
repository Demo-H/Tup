package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.tupperware.biz.R;
import com.tupperware.biz.adapter.CourseContentListAdapter;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.entity.college.CourseBean;
import com.tupperware.biz.entity.college.CourseContentBean;
import com.tupperware.biz.listener.ICourseVideoListener;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.data.DataHelper;
import com.tupperware.biz.utils.logutils.LogF;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/18.
 * 课程大纲
 */

public class CourseContentListFragment extends BaseFragment {

    private static final String TAG = "CourseContentListFragment";

    @BindView(R.id.course_recyclerview)
    RecyclerView mRecyclerView;
    private ICourseVideoListener mListener;

    private CourseContentListAdapter mAdapter;
    public static CourseContentListFragment newInstance() {
        CourseContentListFragment fragment = new CourseContentListFragment();
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
//        }
    }

    public void setListener(ICourseVideoListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
//        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CourseContentListAdapter(mListener);
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_course_content_list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
    }

    @Override
    public void refreshUI(CourseBean courseBean) {
        if(courseBean != null) {
            LogF.i(TAG, ObjectUtil.jsonFormatter(courseBean));
            List<CourseContentBean> list = DataHelper.getDatamatch(courseBean.model.outlineFrontList);
            String string = ObjectUtil.jsonFormatter(list);
            LogF.i(TAG, string);
            mAdapter.setNewData(list);
        }
    }

}
