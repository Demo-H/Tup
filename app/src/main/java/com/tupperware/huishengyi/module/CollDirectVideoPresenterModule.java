package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.CollDirectVideoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/4/18.
 */

@Module
public class CollDirectVideoPresenterModule {
    private CollDirectVideoContract.View view;
    private MainDataManager mainDataManager;

    public CollDirectVideoPresenterModule(CollDirectVideoContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }


    @Provides
    CollDirectVideoContract.View providerMainContractView(){
        return view;
    }
}