package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.LoginDataManager;
import com.tupperware.biz.ui.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/4.
 */

@Module
public class LoginPresenterModule {
    private LoginContract.View view;
    private LoginDataManager mDataManager;

    public LoginPresenterModule(LoginContract.View view, LoginDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    LoginDataManager providerLoginDataManager() {
        return mDataManager;
    }

    @Provides
    LoginContract.View providerLoginContractView(){
        return view;
    }
}