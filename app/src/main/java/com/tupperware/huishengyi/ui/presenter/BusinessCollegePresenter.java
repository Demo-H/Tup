package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.college.CollegeTabBean;
import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.ui.contract.BusinessCollegeContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/4/28.
 */

public class BusinessCollegePresenter extends BasePresenter implements BusinessCollegeContract.Presenter{

    private static final String TAG = "BusinessCollegePresenter";
    private CollegeDataManager mDataManager;

    private BusinessCollegeContract.View mView;

    @Inject
    public BusinessCollegePresenter(CollegeDataManager mDataManager, BusinessCollegeContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getLableData() {
        mDataManager.getCollegeTabData(new ErrorDisposableObserver<CollegeTabBean>() {
            @Override
            public void onNext(CollegeTabBean collegeTabBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(collegeTabBean));
                if(!collegeTabBean.success) {
                    mView.toast(collegeTabBean.message);
                    mView.setNetErrorView();
                } else {
                    mView.setNormalView();
                    mView.setLableData(collegeTabBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
