package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.contract.RemindContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/11/29.
 */
@Module
public class RemindPresenterModule {
    private RemindContract.View view;
    private MemberDataManager mDataManager;

    public RemindPresenterModule(RemindContract.View view, MemberDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    MemberDataManager providerMemberDataManager() {
        return mDataManager;
    }

    @Provides
    RemindContract.View providerRemindContractView(){
        return view;
    }
}