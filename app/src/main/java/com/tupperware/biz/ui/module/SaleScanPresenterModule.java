package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.SaleScanContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/4.
 */
@Module
public class SaleScanPresenterModule {
    private SaleScanContract.View view;
    private ProductDataManager mDataManager;

    public SaleScanPresenterModule(SaleScanContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    SaleScanContract.View providerSaleScanContractView(){
        return view;
    }
}