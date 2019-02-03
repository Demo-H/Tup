package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.InventoryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/11/30.
 */

@Module
public class InventoryPresenterModule {
    private InventoryContract.View view;
    private ProductDataManager mDataManager;

    public InventoryPresenterModule(InventoryContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    InventoryContract.View providerInventoryContractView(){
        return view;
    }
}