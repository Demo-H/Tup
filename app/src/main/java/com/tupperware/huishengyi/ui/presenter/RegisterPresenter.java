package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.entity.saleenter.MemUpgradeRequest;
import com.tupperware.huishengyi.http.MemberDataManager;
import com.tupperware.huishengyi.ui.contract.RegisterContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/7/10.
 */

public class RegisterPresenter extends BasePresenter implements RegisterContract.Presenter {

    private static final String TAG = "RegisterPresenter";
    private MemberDataManager mDataManager;
    private RegisterContract.View mView;

    @Inject
    public RegisterPresenter(MemberDataManager dataManager, RegisterContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    @Override
    public void getSMSCode(String phone) {
        addDisposabe(mDataManager.getSMSCode(new ErrorDisposableObserver<ResponseBean>() {
            @Override
            public void onNext(ResponseBean responseBean) {
                mView.hideDialog();
                if(responseBean != null) {
                    mView.toast(responseBean.getMessage());
                    if(responseBean.isSuccess()) {
                        mView.setSmsCodeResult();
                    } else {
                        mView.setRegisterError();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setRegisterError();
            }

            @Override
            public void onComplete() {

            }
        }, phone));

    }

    @Override
    public void startRegister(MemUpgradeRequest request) {
        addDisposabe(mDataManager.startRegister(new ErrorDisposableObserver<ResponseBean>() {
            @Override
            public void onNext(ResponseBean responseBean) {
                mView.hideDialog();
                if(responseBean != null) {
                    mView.toast(responseBean.getMessage());
                    if(responseBean.isSuccess()) {
                        mView.setRegisterResult();
                    } else {
                        mView.setSMSCodeError();
                    }
                }
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
        }, request));
    }
}
