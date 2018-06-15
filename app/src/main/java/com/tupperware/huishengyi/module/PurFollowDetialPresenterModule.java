package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.PurFollowDetialContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/30.
 */

@Module
public class PurFollowDetialPresenterModule {
    private PurFollowDetialContract.View view;
    private MainDataManager mainDataManager;

    public PurFollowDetialPresenterModule(PurFollowDetialContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    PurFollowDetialContract.View providerMainContractView(){
        return view;
    }
}