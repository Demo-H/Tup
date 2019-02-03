package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.config.Constants;
import com.tupperware.biz.entity.VerifyCoupon;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.CouponVerifyContract;
import com.tupperware.biz.utils.ObjectUtil;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/7/10.
 */

public class CouponVerifyPresenter extends BasePresenter implements CouponVerifyContract.Presenter {

    private static final String TAG = "CouponVerifyPresenter";
    private ProductDataManager mDataManager;
    private CouponVerifyContract.View mView;

    @Inject
    public CouponVerifyPresenter(ProductDataManager dataManager, CouponVerifyContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
    }


    @Override
    public void checkCoupon(String qrCode) {
        addDisposabe(mDataManager.checkCoupon(new ErrorDisposableObserver<VerifyCoupon>() {
            @Override
            public void onNext(VerifyCoupon verifyCoupon) {
                String json = ObjectUtil.jsonFormatter(verifyCoupon);
                mView.hideDialog();
                if(verifyCoupon != null) {
                    if(verifyCoupon.isSuccess()) {
                        mDataManager.saveSPData(Constants.KEY_DATA_SCAN_COUPON, json);
                        mView.setCouponCheckResult(verifyCoupon);
                    } else {
                        mView.toast(verifyCoupon.message);
                        if(verifyCoupon.resultCode != null && (verifyCoupon.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                                || verifyCoupon.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                            mView.reLogin();
                            mDataManager.deleteSPData();
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setErrorShow();
            }

            @Override
            public void onComplete() {

            }
        }, qrCode));
    }
}
