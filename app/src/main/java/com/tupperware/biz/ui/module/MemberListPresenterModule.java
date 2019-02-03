package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.contract.MemberListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/10/22.
 */

@Module
public class MemberListPresenterModule {
    private MemberListContract.View view;
    private MemberDataManager mDataManager;

    public MemberListPresenterModule(MemberListContract.View view, MemberDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    MemberDataManager providerMemberDataManager() {
        return mDataManager;
    }

    @Provides
    MemberListContract.View providerMemberListContractView(){
        return view;
    }
}