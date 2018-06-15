package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.MessageContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/22.
 */

@Module
public class MessagePresenterModule {
    private MessageContract.View view;
    private MainDataManager mDataManager;

    public MessagePresenterModule(MessageContract.View view, MainDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mDataManager;
    }

    @Provides
    MessageContract.View providerMessageContractView(){
        return view;
    }
}