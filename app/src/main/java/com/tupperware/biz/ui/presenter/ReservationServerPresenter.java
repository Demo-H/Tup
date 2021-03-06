package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.ReservationServerBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.ReservationServerContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/31.
 */

public class ReservationServerPresenter extends BasePresenter implements ReservationServerContract.Presenter {

    private static final String TAG = "ReservationServerPresenter";
    private PersonalDataManager mDataManager;
    private ReservationServerContract.View mView;

    @Inject
    public ReservationServerPresenter(PersonalDataManager mDataManager, ReservationServerContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getReservationServerData(String mobile, String storeCode) {
        addDisposabe(mDataManager.getReservationServerData(new ErrorDisposableObserver<ReservationServerBean>() {
            @Override
            public void onNext(ReservationServerBean responeBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(responeBean));
                mView.hideDialog();
                if(!responeBean.success) {
                    mView.toast(responeBean.message);
                    if(responeBean.resultCode != null && (responeBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || responeBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mView.setNetErrorView();
                    }
                } else if(responeBean.getModels() == null || responeBean.getModels().isEmpty()) {
                    mView.setEmptyView();
                } else {
                    mView.setReservationServerData(responeBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        }, mobile, storeCode, Constant.FIRST_PAGE_INDEX));
    }

    @Override
    public void getMoreReservationServerData(String mobile, String storeCode, int indexPage) {
        addDisposabe(mDataManager.getReservationServerData(new ErrorDisposableObserver<ReservationServerBean>() {
            @Override
            public void onNext(ReservationServerBean responeBean) {
                mView.setMoreReservationServerData(responeBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, mobile, storeCode, indexPage));
    }
}
