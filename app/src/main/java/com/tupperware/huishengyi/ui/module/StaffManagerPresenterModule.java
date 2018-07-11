package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.StaffManagerContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/22.
 */

@Module
public class StaffManagerPresenterModule {
    private StaffManagerContract.View view;
    private MainDataManager mainDataManager;

    public StaffManagerPresenterModule(StaffManagerContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    StaffManagerContract.View providerMainContractView(){
        return view;
    }
}