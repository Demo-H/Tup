package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.RemarksContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/6.
 */

@Module
public class RemarksPresenterModule {
    private RemarksContract.View view;
    private PersonalDataManager mDataManager;

    public RemarksPresenterModule(RemarksContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    RemarksContract.View providerRemarksContractView(){
        return view;
    }
}