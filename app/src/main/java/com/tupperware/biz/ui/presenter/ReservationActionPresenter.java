package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.member.ActionMembersBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.ReservationActionContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/14.
 */

public class ReservationActionPresenter extends BasePresenter implements ReservationActionContract.Presenter {

    private static final String TAG = "ReservationActionPresenter";
    private PersonalDataManager mDataManager;
    private ReservationActionContract.View mView;

    @Inject
    public ReservationActionPresenter(PersonalDataManager mDataManager, ReservationActionContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getMemberActionDetialData(long id) {
        addDisposabe(mDataManager.getActionMemberDetial(new ErrorDisposableObserver<ActionMembersBean>() {
            @Override
            public void onNext(ActionMembersBean actionMembersBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(actionMembersBean));
                mView.hideDialog();
                if(!actionMembersBean.success) {
                    mView.toast(actionMembersBean.message);
                    if(actionMembersBean.resultCode != null && (actionMembersBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || actionMembersBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    mView.setMemberActionDetialData(actionMembersBean);
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
        }, id));
    }

}
