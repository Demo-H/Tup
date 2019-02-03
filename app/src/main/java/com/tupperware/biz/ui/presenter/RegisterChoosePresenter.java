package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.login.ResponseBean;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.RegisterChooseContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/7/10.
 */

public class RegisterChoosePresenter extends BasePresenter implements RegisterChooseContract.Presenter {

    private static final String TAG = "RegisterChoosePresenter";
    private ProductDataManager mDataManager;
    private RegisterChooseContract.View mView;

    @Inject
    public RegisterChoosePresenter(ProductDataManager dataManager, RegisterChooseContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }

    @Override
    public void useProductCoupon(String qrCode, String uniqueCode, Integer memberId, int isUpgrade) {
        final Integer storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, -1);
        addDisposabe(mDataManager.useProductCode(new ErrorDisposableObserver<ResponseBean>() {
            @Override
            public void onNext(ResponseBean responseBean) {
                mView.hideDialog();
                if(responseBean != null) {
//                    if(responseBean.getMessage() != null && !responseBean.getMessage().isEmpty()) {
//                        mView.toast(responseBean.getMessage());
//                    }
                    if(responseBean.isSuccess()) {
                        mView.setUseCouponResult(responseBean.getMessage());
                    } else {
                        if(responseBean.resultCode != null && (responseBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                        || responseBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                            mView.reLogin();
                            mDataManager.deleteSPData();
                        }
                        mView.toast(responseBean.getMessage());
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
        }, qrCode, uniqueCode, memberId, isUpgrade, storeId));
    }
}
