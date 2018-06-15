package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.ProductSearchContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/29.
 */

@Module
public class ProductSearchPresenterModule {
    private ProductSearchContract.View view;
    private ProductDataManager mDataManager;

    public ProductSearchPresenterModule(ProductSearchContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    ProductSearchContract.View providerProductSearchContractView(){
        return view;
    }
}