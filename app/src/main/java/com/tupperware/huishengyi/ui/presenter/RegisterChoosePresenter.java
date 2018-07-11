package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.RegisterChooseContract;

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
