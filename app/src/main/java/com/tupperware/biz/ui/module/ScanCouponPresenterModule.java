package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.ScanCouponContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/11.
 */

@Module
public class ScanCouponPresenterModule {
    private ScanCouponContract.View view;
    private ProductDataManager mDataManager;

    public ScanCouponPresenterModule(ScanCouponContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    ScanCouponContract.View providerScanCouponContractView(){
        return view;
    }
}