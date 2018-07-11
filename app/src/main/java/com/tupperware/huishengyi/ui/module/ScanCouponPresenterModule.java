package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.ScanCouponContract;

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