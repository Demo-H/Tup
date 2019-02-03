package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.ProductEnterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/23.
 */

@Module
public class ProductEnterPresenterModule {
    private ProductEnterContract.View view;
    private MainDataManager mainDataManager;

    public ProductEnterPresenterModule(ProductEnterContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    ProductEnterContract.View providerMainContractView(){
        return view;
    }
}