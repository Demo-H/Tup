package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.ResOrderServerContract;

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