package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.SaleEnterClassifyContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/28.
 */

@Module
public class SaleEnterClassifyPresenterModule {
    private SaleEnterClassifyContract.View view;
    private ProductDataManager mDataManager;

    public SaleEnterClassifyPresenterModule(SaleEnterClassifyContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    SaleEnterClassifyContract.View providerSaleEnterClassifyContractView(){
        return view;
    }
}