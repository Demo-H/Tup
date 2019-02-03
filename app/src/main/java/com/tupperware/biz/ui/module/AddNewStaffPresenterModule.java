package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.AddNewStaffContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/10/24.
 */

@Module
public class AddNewStaffPresenterModule {
    private AddNewStaffContract.View view;
    private PersonalDataManager mDataManager;

    public AddNewStaffPresenterModule(AddNewStaffContract.View view, PersonalDataManager DataManager) {
        this.view = view;
        this.mDataManager = DataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    AddNewStaffContract.View providerAddNewStaffContractView(){
        return view;
    }
}