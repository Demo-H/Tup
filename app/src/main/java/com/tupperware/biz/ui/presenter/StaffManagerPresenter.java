package com.tupperware.biz.ui.presenter;


import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.StaffManagerBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.StaffManagerContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerPresenter extends BasePresenter implements StaffManagerContract.Presenter {

    private PersonalDataManager mDataManager;

    private StaffManagerContract.View mStaffManagerView;

    private Activity activity;

    @Inject
    public StaffManagerPresenter(PersonalDataManager mDataManager, StaffManagerContract.View view) {
        this.mDataManager = mDataManager;
        this.mStaffManagerView = view;

    }

    @Override
    public void getStaffManagerData(Integer storeId) {
        addDisposabe(mDataManager.getStaffListData(new ErrorDisposableObserver<StaffManagerBean>() {
            @Override
            public void onNext(StaffManagerBean staffManagerBean) {
                mStaffManagerView.hideDialog();
                if(!staffManagerBean.isSuccess()) {
                    mStaffManagerView.toast(staffManagerBean.getMessage());
                    if(staffManagerBean.getResultCode() != null && (staffManagerBean.getResultCode().equals(StateCode.TOKEN_OUT_DATE_S)
                            || staffManagerBean.getResultCode().equals(StateCode.TOKEN_OUT_DATE))) {
                        mStaffManagerView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mStaffManagerView.setNetErrorView();
                    }
                } else {
                    if(staffManagerBean.getModels() != null) {
                        mStaffManagerView.setStaffManagerData(staffManagerBean);
                    } else {
                        mStaffManagerView.setEmptyView();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mStaffManagerView.hideDialog();
                mStaffManagerView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        }, storeId));
    }
}
