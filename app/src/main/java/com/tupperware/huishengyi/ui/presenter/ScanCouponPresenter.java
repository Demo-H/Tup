package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.VerifyCoupon;
import com.tupperware.huishengyi.entity.VerifyProduct;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.ScanCouponContract;
import com.tupperware.huishengyi.utils.ObjectUtil;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/7/11.
 */

public class ScanCouponPresenter extends BasePresenter implements ScanCouponContract.Presenter {

    private static final String TAG = "ScanCouponPresenter";
    private ProductDataManager mDataManager;
    private ScanCouponContract.View mView;

    @Inject
    public ScanCouponPresenter(ProductDataManager dataManager, ScanCouponContract.View view) {
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
                        mView.setErrorShow(verifyCoupon.getMessage());
                        mView.toast(verifyCoupon.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setErrorShow(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, qrCode));
    }

    @Override
    public void verifyProductCode(String qrCode, String uniqueCode) {
        addDisposabe(mDataManager.verifyProductCode(new ErrorDisposableObserver<VerifyProduct>() {
            @Override
            public void onNext(VerifyProduct product) {
                String json = ObjectUtil.jsonFormatter(product);
                mView.hideDialog();
                if(product != null) {
                    if(product.isSuccess()) {
                        mView.setProductVerifyResult(product);
                        mDataManager.saveSPData(Constants.KEY_DATA_SCAN_PRODUCT_JSON, json);
                        mDataManager.saveSPObjectData(Constants.FANS_MEMBER_ID, product.model.getMembers().getMember_id());
                    } else if(!product.getModel().isCoupon_can_be_used()) {
                        mView.toast(product.getModel().getCant_be_used_reason());
                        mView.setErrorShow(product.getMessage());
                    } else {
                        mView.setErrorShow(product.getMessage());
                        mView.toast(product.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setErrorShow(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, qrCode, uniqueCode));
    }
}
