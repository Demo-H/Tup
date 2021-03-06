package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.VersionCheckContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/4/25.
 */

@Module
public class VersionCheckPresenterModule {
    private VersionCheckContract.View view;
    private MainDataManager mainDataManager;

    public VersionCheckPresenterModule(VersionCheckContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    VersionCheckContract.View providerMainContractView(){
        return view;
    }
}
