package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.ZixunContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/6.
 */

@Module
public class ZixunPresenterModule {
    private ZixunContract.View view;
    private MainDataManager mainDataManager;

    public ZixunPresenterModule(ZixunContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    ZixunContract.View providerMainContractView(){
        return view;
    }
}
