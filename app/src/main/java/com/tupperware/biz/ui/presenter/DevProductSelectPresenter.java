package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.member.DevMemberRequest;
import com.tupperware.biz.entity.member.DevMemberRespone;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.DevProductSelectContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;


/**
 * Created by dhunter on 2018/6/6.
 */

public class DevProductSelectPresenter extends BasePresenter implements DevProductSelectContract.Presenter {

    private static final String TAG = "DevProductSelectPresenter";
    private PersonalDataManager mDataManager;
    private DevProductSelectContract.View mView;

    @Inject
    public DevProductSelectPresenter(PersonalDataManager mDataManager, DevProductSelectContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void submitmemberLabel(DevMemberRequest devMemberRequest) {
        addDisposabe(mDataManager.getDevMemberData(new ErrorDisposableObserver<DevMemberRespone>() {
            @Override
            public void onNext(DevMemberRespone devMemberRespone) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(devMemberRespone));
                mView.hideDialog();
                if(!devMemberRespone.success) {
                    mView.toast(devMemberRespone.message);
                    if(devMemberRespone.resultCode != null && (devMemberRespone.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                            || devMemberRespone.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    mView.setDevMemberData(devMemberRespone);
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
        }, devMemberRequest));
    }
}
