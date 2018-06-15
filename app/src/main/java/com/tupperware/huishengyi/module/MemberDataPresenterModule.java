package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.MemberDataContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/12.
 */

@Module
public class MemberDataPresenterModule {
    private MemberDataContract.View view;
    private PersonalDataManager mDataManager;

    public MemberDataPresenterModule(MemberDataContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    MemberDataContract.View providerMemberDataContractView(){
        return view;
    }
}