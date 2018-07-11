package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.VerifyProduct;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.ProductVerifyContract;
import com.tupperware.huishengyi.utils.ObjectUtil;

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
                        mDataManager.saveSPObjectData(Constants.FANS_MEMBER_ID, product.model.getMembers().getMember_id());
                        mView.setProductVerifyResult(product);
                    } else {
                        mView.toast(product.getMessage());
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
    public void useProductCoupon(String qrCode, String uniqueCode) {
        addDisposabe(mDataManager.useProductCode(new ErrorDisposableObserver<ResponseBean>() {
            @Override
            public void onNext(ResponseBean responseBean) {
                mView.hideDialog();
                if(responseBean != null) {
                    mView.toast(responseBean.getMessage());
                    if(responseBean.isSuccess()) {
                        mView.setUseCouponResult();
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
        }, qrCode, uniqueCode));
    }
}
