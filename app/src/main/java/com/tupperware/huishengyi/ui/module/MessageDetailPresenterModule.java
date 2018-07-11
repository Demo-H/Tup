package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.MessageDetialContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/26.
 */

@Module
public class MessageDetailPresenterModule {
    private MessageDetialContract.View view;
    private MainDataManager mainDataManager;

    public MessageDetailPresenterModule(MessageDetialContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    MessageDetialContract.View providerMainContractView(){
        return view;
    }
}