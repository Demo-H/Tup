package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.DevProductSelectContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/6.
 */

@Module
public class DevProductSelectPresenterModule {
    private DevProductSelectContract.View view;
    private PersonalDataManager mDataManager;

    public DevProductSelectPresenterModule(DevProductSelectContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    DevProductSelectContract.View providerDevProductSelectContractView(){
        return view;
    }
}