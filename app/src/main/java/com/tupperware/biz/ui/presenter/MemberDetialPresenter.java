package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.MemberDetialContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/31.
 */

public class MemberDetialPresenter extends BasePresenter implements MemberDetialContract.Presenter {

    private static final String TAG = "MemberDetialPresenter";

    private MemberDataManager mDataManager;
    private MemberDetialContract.View mView;

    @Inject
    public MemberDetialPresenter(MemberDataManager mDataManager, MemberDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getMemberDetialData(Integer memberId, String mobileNum, int storeId, String employeeCode) {
        addDisposabe(mDataManager.getMemberDetialData(new ErrorDisposableObserver<MemberBean>() {
            @Override
            public void onNext(MemberBean memberBean) {
                mView.hideDialog();
                if(memberBean != null) {
                    if(!memberBean.isSuccess()) {
                        mView.toast(memberBean.getMessage());
                        if(memberBean.resultCode != null && (memberBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                                || memberBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                            mView.reLogin();
                            mDataManager.deleteSPData();
                        }
                    } else {
                        mView.refreshUIData(memberBean);
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
        }, memberId, mobileNum, storeId, employeeCode));
    }
}
