package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.LoveVipContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/8.
 */

@Module
public class LoveVipPresenterModule {
    private LoveVipContract.View view;
    private MainDataManager mainDataManager;

    public LoveVipPresenterModule(LoveVipContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    LoveVipContract.View providerMainContractView(){
        return view;
    }
}