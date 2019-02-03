package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.BenefitCoinDeadlineContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/4/2.
 */

@Module
public class BenefitCoinDeadlinePresenterModule {
    private BenefitCoinDeadlineContract.View view;
    private MainDataManager mainDataManager;

    public BenefitCoinDeadlinePresenterModule(BenefitCoinDeadlineContract.View view,  MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    BenefitCoinDeadlineContract.View providerMainContractView(){
        return view;
    }
}