package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;
import android.util.Log;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.ui.contract.CollExperienceContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

import static cn.jzvd.JZMediaManager.TAG;

/**
 * Created by dhunter on 2018/4/18.
 */

public class CollExperiencePresenter extends BasePresenter implements CollExperienceContract.Presenter{
    private CollegeDataManager mDataManager;

    private CollExperienceContract.View mView;

    private Activity activity;

    @Inject
    public CollExperiencePresenter(CollegeDataManager mDataManager, CollExperienceContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getExperienceData(int tagId) {
        addDisposabe(mDataManager.getExperienceData(new ErrorDisposableObserver<CollegeBean>() {
            @Override
            public void onNext(CollegeBean collegeBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(collegeBean));
                if(!collegeBean.success) {
                    mView.toast(collegeBean.message);
                } else if(collegeBean.models == null || collegeBean.models.isEmpty()) {
                    mView.setEmptyView();
                } else {
                    mView.setNormalView();
                    mView.setExperienceData(collegeBean);
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e("TAG", "onError: "+e );
                mView.setNetErrorView();
            }
        }, tagId));
    }

    @Override
    public void getMoreExperienceData(int tagId, int pageIndex) {
        addDisposabe(mDataManager.getMoreExperienceData(new ErrorDisposableObserver<CollegeBean>() {
            @Override
            public void onNext(CollegeBean collegeBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(collegeBean));
                if(!collegeBean.success) {
                    mView.toast(collegeBean.message);
                } else {
                    mView.setMoreExperienceData(collegeBean);
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
        }, tagId, pageIndex));
    }
}