package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.KeyProductRecordContract;

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