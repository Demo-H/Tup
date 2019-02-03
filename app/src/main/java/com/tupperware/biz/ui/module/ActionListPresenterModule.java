package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.ActionListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/7.
 */

@Module
public class ActionListPresenterModule {
    private ActionListContract.View view;
    private PersonalDataManager mDataManager;

    public ActionListPresenterModule(ActionListContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    ActionListContract.View providerActionListContractView(){
        return view;
    }
}