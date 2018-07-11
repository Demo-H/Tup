package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.VerifyCoupon;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.CouponVerifyContract;
import com.tupperware.huishengyi.utils.ObjectUtil;

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
                        mView.setCouponCheckResult(verifyCoupon);
                        mDataManager.saveSPData(Constants.KEY_DATA_SCAN_COUPON, json);
                    } else {
                        mView.toast(verifyCoupon.getMessage());
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
