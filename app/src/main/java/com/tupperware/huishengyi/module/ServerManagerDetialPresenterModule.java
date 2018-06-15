package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.ServerManagerDetialContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/30.
 */

@Module
public class ServerManagerDetialPresenterModule {
    private ServerManagerDetialContract.View view;
    private MainDataManager mainDataManager;

    public ServerManagerDetialPresenterModule(ServerManagerDetialContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    ServerManagerDetialContract.View providerMainContractView(){
        return view;
    }
}