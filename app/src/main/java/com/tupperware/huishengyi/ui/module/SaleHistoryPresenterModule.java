package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.SaleHistoryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/28.
 */

@Module
public class SaleHistoryPresenterModule {
    private SaleHistoryContract.View view;
    private ProductDataManager mDataManager;

    public SaleHistoryPresenterModule(SaleHistoryContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    SaleHistoryContract.View providerSaleHistoryContractView(){
        return view;
    }
}