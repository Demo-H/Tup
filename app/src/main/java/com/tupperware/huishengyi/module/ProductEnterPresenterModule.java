package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.ProductEnterContract;

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