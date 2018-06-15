package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.EnterListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/5.
 */
@Module
public class EnterListPresenterModule {
    private EnterListContract.View view;
    private ProductDataManager mDataManager;

    public EnterListPresenterModule(EnterListContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    EnterListContract.View providerEnterListContractView(){
        return view;
    }
}