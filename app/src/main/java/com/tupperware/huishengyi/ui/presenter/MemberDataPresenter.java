package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.member.MemberAddBean;
import com.tupperware.huishengyi.entity.member.MemberReportBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.MemberDataContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

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
    public void getMemberReportData(String storeId) {
        addDisposabe(mDataManager.getMemberReportData(new ErrorDisposableObserver<MemberReportBean>() {
            @Override
            public void onNext(MemberReportBean memberReportBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(memberReportBean));
                mView.hideDialog();
                if(!memberReportBean.success) {
                    mView.toast(memberReportBean.message);
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
    public void getTodayNewAddData(String storeId) {
        addDisposabe(mDataManager.getTodayNewAddData(new ErrorDisposableObserver<MemberAddBean>() {
            @Override
            public void onNext(MemberAddBean memberAddBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(memberAddBean));
                mView.hideDialog();
                if(!memberAddBean.success) {
                    mView.toast(memberAddBean.message);
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
