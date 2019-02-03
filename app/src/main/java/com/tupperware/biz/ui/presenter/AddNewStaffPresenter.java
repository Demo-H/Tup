package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.BaseResponse;
import com.tupperware.biz.entity.StaffRequest;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.AddNewStaffContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/10/24.
 */

public class AddNewStaffPresenter extends BasePresenter implements AddNewStaffContract.Presenter {

    private PersonalDataManager mDataManager;

    private AddNewStaffContract.View mView;


    @Inject
    public AddNewStaffPresenter(PersonalDataManager mDataManager, AddNewStaffContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void addStaffData(StaffRequest reqData) {
        addDisposabe(mDataManager.addStaffData(new ErrorDisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse bean) {
                mView.hideDialog();
                mView.toast(bean.getMessage());
                if(!bean.isSuccess()) {
                    if(bean.getResultCode() != null && (bean.getResultCode().equals(StateCode.TOKEN_OUT_DATE_S)
                            || bean.getResultCode().equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                   mView.addStaffDatasuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
            }

            @Override
            public void onComplete() {

            }
        }, reqData));
    }

    @Override
    public void updateStaffData(StaffRequest reqData) {
        addDisposabe(mDataManager.updateStaffData(new ErrorDisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse bean) {
                mView.hideDialog();
                mView.toast(bean.getMessage());
                if(!bean.isSuccess()) {
                    if(bean.getResultCode() != null && (bean.getResultCode().equals(StateCode.TOKEN_OUT_DATE_S)
                            || bean.getResultCode().equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    mView.updateStaffDatasuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
            }

            @Override
            public void onComplete() {

            }
        }, reqData));
    }

}