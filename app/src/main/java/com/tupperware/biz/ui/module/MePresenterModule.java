package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.MeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/9/6.
 */

@Module
public class MePresenterModule {
    private MeContract.View view;
    private PersonalDataManager mDataManager;

    public MePresenterModule(MeContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    MeContract.View providerMeContractView(){
        return view;
    }
}