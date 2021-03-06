package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.MemberBenefitDetialContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/4/2.
 */

@Module
public class MemberBenefitDetialPresenterModule {
    private MemberBenefitDetialContract.View view;
    private MainDataManager mainDataManager;

    public MemberBenefitDetialPresenterModule(MemberBenefitDetialContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }


    @Provides
    MemberBenefitDetialContract.View providerMainContractView(){
        return view;
    }
}