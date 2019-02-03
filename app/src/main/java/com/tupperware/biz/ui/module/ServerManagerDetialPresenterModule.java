package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.ServerManagerDetialContract;

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