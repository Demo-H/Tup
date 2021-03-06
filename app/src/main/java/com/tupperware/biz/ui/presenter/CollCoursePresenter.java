package com.tupperware.biz.ui.presenter;

import android.app.Activity;
import android.util.Log;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.college.CollegeBean;
import com.tupperware.biz.http.CollegeDataManager;
import com.tupperware.biz.ui.contract.CollCourseContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

import static cn.jzvd.JZMediaManager.TAG;

/**
 * Created by dhunter on 2018/4/17.
 */

public class CollCoursePresenter extends BasePresenter implements CollCourseContract.Presenter{
    private CollegeDataManager mDataManager;

    private CollCourseContract.View mView;

    private Activity activity;

    @Inject
    public CollCoursePresenter(CollegeDataManager mDataManager, CollCourseContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getCourseData(int tagId) {
        addDisposabe(mDataManager.getCourseData(new ErrorDisposableObserver<CollegeBean>() {
            @Override
            public void onNext(CollegeBean collegeBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(collegeBean));
                if(!collegeBean.success) {
                    mView.toast(collegeBean.message);
                } else if(collegeBean.models == null || collegeBean.models.isEmpty()){
                    mView.setEmptyView();
                } else {
                    mView.setCourseData(collegeBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError: "+e );
                mView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        }, tagId));
    }

    @Override
    public void getMoreCourseData(int tagId, int pageIndex) {
        addDisposabe(mDataManager.getMoreCourseData(new ErrorDisposableObserver<CollegeBean>() {
            @Override
            public void onNext(CollegeBean collegeBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(collegeBean));
                if(!collegeBean.success) {
                    mView.toast(collegeBean.message);
                } else {
                    mView.setMoreCourseData(collegeBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError: "+e );
            }

            @Override
            public void onComplete() {

            }
        }, tagId, pageIndex));
    }

    @Override
    public void getAdvertData() {
        addDisposabe(mDataManager.getAdvertData(new ErrorDisposableObserver<CollegeBean>() {
            @Override
            public void onNext(CollegeBean collegeBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(collegeBean));
                if(!collegeBean.success) {
                    mView.toast(collegeBean.message);
                }else if(collegeBean.models == null || collegeBean.models.isEmpty()) {
                    mView.setBannerViewHide();
                } else {
                    mView.setNormalView();
                    mView.setBannerView(collegeBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
