package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.login.ModifiedPwdRequest;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.ResetPasswordContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/7/6.
 */

public class ResetPasswordPresenter extends BasePresenter implements ResetPasswordContract.Presenter {

    private static final String TAG = "ResetPasswordPresenter";
    private PersonalDataManager mDataManager;
    private ResetPasswordContract.View mView;

    @Inject
    public ResetPasswordPresenter(PersonalDataManager mDataManager, ResetPasswordContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void modifiedPwd(ModifiedPwdRequest request) {
        addDisposabe(mDataManager.modifiedPwd(new ErrorDisposableObserver<ResponseBean>() {
            @Override
            public void onNext(ResponseBean responseBean) {
                mView.hideDialog();
                if(responseBean == null) {
                    mView.toast("获取收据为空");
                } else if(!responseBean.isSuccess()) {
                    mView.toast(responseBean.getMessage());
                } else {
                    mView.toast("修改密码成功，请重新登录");
                    mView.setModifiedPwdSuccess();
                    mDataManager.deleteSPData();
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
