package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.contract.GiftListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/7.
 */

@Module
public class GiftListPresenterModule {
    private GiftListContract.View view;
    private MemberDataManager mDataManager;

    public GiftListPresenterModule(GiftListContract.View view, MemberDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    MemberDataManager providerMemberDataManager() {
        return mDataManager;
    }

    @Provides
    GiftListContract.View providerGiftListContractView(){
        return view;
    }
}