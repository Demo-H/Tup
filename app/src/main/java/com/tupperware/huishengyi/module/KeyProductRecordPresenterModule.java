package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.KeyProductRecordContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/22.
 */

@Module
public class KeyProductRecordPresenterModule {
    private KeyProductRecordContract.View view;
    private MainDataManager mainDataManager;

    public KeyProductRecordPresenterModule(KeyProductRecordContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    KeyProductRecordContract.View providerMainContractView(){
        return view;
    }
}