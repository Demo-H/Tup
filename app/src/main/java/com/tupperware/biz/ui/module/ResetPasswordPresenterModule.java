package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.ResetPasswordContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/6.
 */

@Module
public class ResetPasswordPresenterModule {
    private ResetPasswordContract.View view;
    private PersonalDataManager mDataManager;

    public ResetPasswordPresenterModule(ResetPasswordContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    ResetPasswordContract.View providerResetPasswordContractView(){
        return view;
    }
}