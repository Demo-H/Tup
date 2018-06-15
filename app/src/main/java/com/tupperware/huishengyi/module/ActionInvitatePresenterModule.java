package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.ActionInvitateContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/14.
 */

@Module
public class ActionInvitatePresenterModule {
    private ActionInvitateContract.View view;
    private PersonalDataManager mDataManager;

    public ActionInvitatePresenterModule(ActionInvitateContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    ActionInvitateContract.View providerActionInvitateContractView(){
        return view;
    }
}