package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.member.RemarksRequest;
import com.tupperware.biz.entity.saleenter.ResponeBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.RemarksContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/6.
 */

public class RemarksPresenter extends BasePresenter implements RemarksContract.Presenter{

    private static final String TAG = "RemarksPresenter";
    private PersonalDataManager mDataManager;
    private RemarksContract.View mView;

    @Inject
    public RemarksPresenter(PersonalDataManager mDataManager, RemarksContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void submitRemarks(RemarksRequest remarksRequest) {
        addDisposabe(mDataManager.submitRemarks(new ErrorDisposableObserver<ResponeBean>() {
            @Override
            public void onNext(ResponeBean responeBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(responeBean));
                mView.hideDialog();
                if(!responeBean.success) {
                    mView.toast(responeBean.message);
                    if(responeBean.resultCode != null && (responeBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || responeBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    mView.setremarksSuccess();
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
        }, remarksRequest));
    }
}
