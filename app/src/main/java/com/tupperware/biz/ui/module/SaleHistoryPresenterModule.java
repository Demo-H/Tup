package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.SaleHistoryContract;

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