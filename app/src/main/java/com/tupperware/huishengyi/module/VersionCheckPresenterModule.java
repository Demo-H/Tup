package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.VersionCheckContract;

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
