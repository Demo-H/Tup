package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.SaleDataDetialContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/11.
 */

@Module
public class SaleDataDetialPresenterModule {
    private SaleDataDetialContract.View view;
    private ProductDataManager mDataManager;

    public SaleDataDetialPresenterModule(SaleDataDetialContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    SaleDataDetialContract.View providerSaleDataDetialContractView(){
        return view;
    }
}