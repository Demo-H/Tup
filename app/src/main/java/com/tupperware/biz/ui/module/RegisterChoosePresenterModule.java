package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.contract.RegisterChooseContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/10.
 */


@Module
public class RegisterChoosePresenterModule {
    private RegisterChooseContract.View view;
    private ProductDataManager mDataManager;

    public RegisterChoosePresenterModule(RegisterChooseContract.View view, ProductDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    ProductDataManager providerProductDataManager() {
        return mDataManager;
    }

    @Provides
    RegisterChooseContract.View providerRegisterChooseContractView(){
        return view;
    }
}