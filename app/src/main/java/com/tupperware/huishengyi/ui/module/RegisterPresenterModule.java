package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.MemberDataManager;
import com.tupperware.huishengyi.ui.contract.RegisterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/10.
 */

@Module
public class RegisterPresenterModule {
    private RegisterContract.View view;
    private MemberDataManager mDataManager;

    public RegisterPresenterModule(RegisterContract.View view, MemberDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    MemberDataManager providerMemberDataManager() {
        return mDataManager;
    }

    @Provides
    RegisterContract.View providerRegisterContractView(){
        return view;
    }
}