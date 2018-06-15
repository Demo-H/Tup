package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.member.RemarksRequest;
import com.tupperware.huishengyi.entity.saleenter.ResponeBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.RemarksContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

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
