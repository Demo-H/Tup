package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.login.ForgetPwInfo;
import com.tupperware.huishengyi.entity.login.ResetPwdRequest;
import com.tupperware.huishengyi.http.LoginDataManager;
import com.tupperware.huishengyi.ui.contract.ForgetPasswordContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/7/6.
 */

public class ForgetPasswordPresenter extends BasePresenter implements ForgetPasswordContract.Presenter {

    private static final String TAG = "ForgetPasswordPresenter";
    private LoginDataManager mDataManager;
    private ForgetPasswordContract.View mView;

    @Inject
    public ForgetPasswordPresenter(LoginDataManager mDataManager, ForgetPasswordContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getPhonebyStore(String storeEmployeeCode) {
        addDisposabe(mDataManager.getPhonebyStore(new ErrorDisposableObserver<ForgetPwInfo>() {
            @Override
            public void onNext(ForgetPwInfo forgetPwInfo) {
                mView.hideDialog();
                if(forgetPwInfo == null) {
                    mView.toast("获取数据为空");
                } else if(!forgetPwInfo.isSuccess()) {
                    mView.toast(forgetPwInfo.getMessage());
                } else {
                    mView.setPhoneData(forgetPwInfo);
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
        }, storeEmployeeCode));
    }

    @Override
    public void getSMSCode(String phoneNum) {
        addDisposabe(mDataManager.getSMSCode(new ErrorDisposableObserver<ForgetPwInfo>() {
            @Override
            public void onNext(ForgetPwInfo forgetPwInfo) {
                mView.hideDialog();
                mView.setSMSCodeSuccess();
                mView.toast(forgetPwInfo.getMessage());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setSMSCodeError();
            }

            @Override
            public void onComplete() {

            }
        }, phoneNum));
    }

    @Override
    public void forgetResetPwd(ResetPwdRequest request) {
        addDisposabe(mDataManager.forgetResetPwd(new ErrorDisposableObserver<ForgetPwInfo>() {
            @Override
            public void onNext(ForgetPwInfo forgetPwInfo) {
                mView.hideDialog();
                if(forgetPwInfo != null) {
                    mView.toast(forgetPwInfo.getMessage());
                    if(forgetPwInfo.isSuccess()) {
                        mView.setResetPwdSuccess();
                    }
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
        }, request));
    }
}
