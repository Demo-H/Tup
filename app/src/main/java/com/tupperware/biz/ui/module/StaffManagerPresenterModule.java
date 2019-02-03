package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.StaffManagerContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/22.
 */

@Module
public class StaffManagerPresenterModule {
    private StaffManagerContract.View view;
    private PersonalDataManager mDataManager;

    public StaffManagerPresenterModule(StaffManagerContract.View view, PersonalDataManager DataManager) {
        this.view = view;
        this.mDataManager = DataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    StaffManagerContract.View providerStaffManagerContractView(){
        return view;
    }
}