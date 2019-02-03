package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.SaleHandContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/25.
 */


@Module
public class SaleHandPresenterModule {
    private SaleHandContract.View view;
    private ProductDataManager mDataManager;

    public SaleHandPresenterModule(SaleHandContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    SaleHandContract.View providerSaleHandContractView(){
        return view;
    }
}