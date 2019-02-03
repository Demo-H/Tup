package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.entity.college.CourseBean;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by dhunter on 2018/5/10.
 * 课程介绍
 */

public class CourseIntroduceFragment extends BaseFragment {
    private static final String TAG = "CourseIntroduceFragment";

    @BindView(R.id.webview)
    WebView mWebView;

    public static CourseIntroduceFragment newInstance() {
        CourseIntroduceFragment fragment = new CourseIntroduceFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//        String html = "<h1>qqq</h1>";
//        mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_course_introduce;
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
            mWebView.loadDataWithBaseURL(null, courseBean.model.content, "text/html", "utf-8", null);
        }
    }
}
