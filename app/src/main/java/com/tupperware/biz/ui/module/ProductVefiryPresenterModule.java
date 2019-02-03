package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.ProductVerifyContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/10.
 */

@Module
public class ProductVefiryPresenterModule {
    private ProductVerifyContract.View view;
    private ProductDataManager mDataManager;

    public ProductVefiryPresenterModule(ProductVerifyContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    ProductVerifyContract.View providerProductVerifyContractView(){
        return view;
    }
}