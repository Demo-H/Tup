package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.HomePageContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/12.
 */

@Module
public class HomePagePresenterModule {
    private HomePageContract.View view;
    private MainDataManager mainDataManager;

    public HomePagePresenterModule(HomePageContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    HomePageContract.View providerMainContractView(){
        return view;
    }
}