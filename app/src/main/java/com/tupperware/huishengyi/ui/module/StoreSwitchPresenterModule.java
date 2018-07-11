package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.StoreSwitchContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/19.
 */

@Module
public class StoreSwitchPresenterModule {
    private StoreSwitchContract.View view;
    private MainDataManager mainDataManager;

    public StoreSwitchPresenterModule(StoreSwitchContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    StoreSwitchContract.View providerMainContractView(){
        return view;
    }
}
