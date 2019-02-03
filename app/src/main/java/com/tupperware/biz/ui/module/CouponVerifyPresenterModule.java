package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.CouponVerifyContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/10.
 */

@Module
public class CouponVerifyPresenterModule {
    private CouponVerifyContract.View view;
    private ProductDataManager mDataManager;

    public CouponVerifyPresenterModule(CouponVerifyContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    CouponVerifyContract.View providerCouponVerifyContractView(){
        return view;
    }
}