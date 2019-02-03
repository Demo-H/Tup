package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.MeBenfitCoin;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.MeContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/9/6.
 */

public class MePresenter extends BasePresenter implements MeContract.Presenter {

    private static final String TAG = "MePresenter";
    private PersonalDataManager mDataManager;
    private MeContract.View mView;

    @Inject
    public MePresenter(PersonalDataManager mDataManager, MeContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getMeData(Integer storeId) {
        addDisposabe(mDataManager.getBenifitCoinNum(new ErrorDisposableObserver<MeBenfitCoin>() {
            @Override
            public void onNext(MeBenfitCoin meBenfitCoin) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(meBenfitCoin));
                mView.hideDialog();
                if(!meBenfitCoin.isSuccess()) {
                    mView.toast(meBenfitCoin.getMessage());
                    if(meBenfitCoin.getResultCode() != null && (meBenfitCoin.getResultCode().equals(StateCode.TOKEN_OUT_DATE_S)
                    || meBenfitCoin.getResultCode().equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    if(meBenfitCoin.getModel() != null) {
                        mView.setMeData(meBenfitCoin);
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
        }, storeId));
    }
}
