package com.tupperware.huishengyi.ui.presenter;

import android.util.Log;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.college.CourseBean;
import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.ui.contract.CourseDetialContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

import static cn.jzvd.JZMediaManager.TAG;

/**
 * Created by dhunter on 2018/5/9.
 */

public class CourseDetialPresenter extends BasePresenter implements CourseDetialContract.Presenter {

    private CollegeDataManager mDataManager;

    private CourseDetialContract.View mView;

    @Inject
    public CourseDetialPresenter(CollegeDataManager mDataManager, CourseDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getCourseDetialData(long tagId) {
        addDisposabe(mDataManager.getCourDetialData(new ErrorDisposableObserver<CourseBean>() {
            @Override
            public void onNext(CourseBean courseBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(courseBean));
                if(!courseBean.success) {
                    mView.toast(courseBean.message);
                } else {
                    mView.setCourseDetialData(courseBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e("TAG", "onError: "+e );
            }

            @Override
            public void onComplete() {

            }
        }, tagId));
    }
}
