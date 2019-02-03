package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.config.Constants;
import com.tupperware.biz.entity.VerifyProduct;
import com.tupperware.biz.entity.login.ResponseBean;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.ProductVerifyContract;
import com.tupperware.biz.utils.ObjectUtil;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/7/10.
 */

public class ProductVefiryPresenter extends BasePresenter implements ProductVerifyContract.Presenter {

    private static final String TAG = "ProductVefiryPresenter";
    private ProductDataManager mDataManager;
    private ProductVerifyContract.View mView;

    @Inject
    public ProductVefiryPresenter(ProductDataManager dataManager, ProductVerifyContract.View view) {
        this.mDataManager = dataManager;
        this.mView = view;
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
                        mDataManager.saveSPData(Constants.KEY_DATA_SCAN_PRODUCT_JSON, json);
                        mDataManager.saveSPObjectData(Constants.FANS_MEMBER_ID, product.getModel().getMembers().getMemberId());
                        mView.setProductVerifyResult(product);
                    } else {
                        mView.toast(product.message);
                        if(product.resultCode != null && (product.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                        || product.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
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
        }, qrCode, uniqueCode));
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
