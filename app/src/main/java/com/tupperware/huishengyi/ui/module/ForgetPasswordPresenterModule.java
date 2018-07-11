package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.LoginDataManager;
import com.tupperware.huishengyi.ui.contract.ForgetPasswordContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/6.
 */

@Module
public class ForgetPasswordPresenterModule {
    private ForgetPasswordContract.View view;
    private LoginDataManager mDataManager;

    public ForgetPasswordPresenterModule(ForgetPasswordContract.View view, LoginDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    LoginDataManager providerLoginDataManager() {
        return mDataManager;
    }

    @Provides
    ForgetPasswordContract.View providerForgetPasswordContractView(){
        return view;
    }
}