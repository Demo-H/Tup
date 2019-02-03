package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.member.MemberAddBean;
import com.tupperware.biz.entity.member.MemberReportBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.MemberDataContract;
import com.tupperware.biz.utils.ObjectUtil;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/12.
 */

public class MemberDataPresenter extends BasePresenter implements MemberDataContract.Presenter {

    private static final String TAG = "MemberDataPresenter";
    private PersonalDataManager mDataManager;
    private MemberDataContract.View mView;

    @Inject
    public MemberDataPresenter(PersonalDataManager mDataManager, MemberDataContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getMemberReportData(Integer storeId) {
        addDisposabe(mDataManager.getMemberReportData(new ErrorDisposableObserver<MemberReportBean>() {
            @Override
            public void onNext(MemberReportBean memberReportBean) {
                String json = ObjectUtil.jsonFormatter(memberReportBean);
                mView.hideDialog();
                if(!memberReportBean.success) {
                    mView.toast(memberReportBean.message);
                    if(memberReportBean.resultCode != null && (memberReportBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || memberReportBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    mView.setMemberReportData(memberReportBean);
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
        }, storeId));
    }

    @Override
    public void getTodayNewAddData(Integer storeId) {
        addDisposabe(mDataManager.getTodayNewAddData(new ErrorDisposableObserver<MemberAddBean>() {
            @Override
            public void onNext(MemberAddBean memberAddBean) {
                String json = ObjectUtil.jsonFormatter(memberAddBean);
                mView.hideDialog();
                if(!memberAddBean.success) {
                    mView.toast(memberAddBean.message);
                    if(memberAddBean.resultCode != null && (memberAddBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || memberAddBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    mView.setTodayNewAddData(memberAddBean);
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
        }, storeId));
    }


}
