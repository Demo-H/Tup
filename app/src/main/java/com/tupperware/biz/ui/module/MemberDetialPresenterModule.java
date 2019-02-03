package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.contract.MemberDetialContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/6.
 */
@Module
public class MemberDetialPresenterModule {
    private MemberDetialContract.View view;
    private MemberDataManager mDataManager;

    public MemberDetialPresenterModule(MemberDetialContract.View view, MemberDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    MemberDataManager providerMemberDataManager() {
        return mDataManager;
    }

    @Provides
    MemberDetialContract.View providerMemberDetialContractView(){
        return view;
    }
}
