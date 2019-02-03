package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.ResOrderServerContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/4/8.
 */

@Module
public class ResOrderServerPresenterModule {
    private ResOrderServerContract.View view;
    private MainDataManager mainDataManager;

    public ResOrderServerPresenterModule(ResOrderServerContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    ResOrderServerContract.View providerMainContractView(){
        return view;
    }
}