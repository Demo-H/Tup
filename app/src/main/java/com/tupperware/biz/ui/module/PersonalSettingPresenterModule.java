package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.PersonalSettingContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/5.
 */

@Module
public class PersonalSettingPresenterModule {
    private PersonalSettingContract.View view;
    private PersonalDataManager mDataManager;

    public PersonalSettingPresenterModule(PersonalSettingContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    PersonalSettingContract.View providerPersonalSettingContractView(){
        return view;
    }
}