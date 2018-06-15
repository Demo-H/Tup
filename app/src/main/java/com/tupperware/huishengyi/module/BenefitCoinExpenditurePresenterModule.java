package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.BenefitCoinExpenditureContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/21.
 */

@Module
public class BenefitCoinExpenditurePresenterModule {
    private BenefitCoinExpenditureContract.View view;
    private MainDataManager mainDataManager;

    public BenefitCoinExpenditurePresenterModule(BenefitCoinExpenditureContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    BenefitCoinExpenditureContract.View providerMainContractView(){
        return view;
    }
}