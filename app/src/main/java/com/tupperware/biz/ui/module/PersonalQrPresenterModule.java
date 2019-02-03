package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.PersonalQrContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/4.
 */

@Module
public class PersonalQrPresenterModule {
    private PersonalQrContract.View view;
    private PersonalDataManager mDataManager;

    public PersonalQrPresenterModule(PersonalQrContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    PersonalQrContract.View providerPersonalQrContractView(){
        return view;
    }
}