package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.HotSaleEnterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/12/3.
 */
@Module
public class HotSaleEnterPresenterModule {
    private HotSaleEnterContract.View view;
    private ProductDataManager mDataManager;

    public HotSaleEnterPresenterModule(HotSaleEnterContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    HotSaleEnterContract.View providerHotSaleEnterContractView(){
        return view;
    }
}