package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.MessageDetialContract;

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