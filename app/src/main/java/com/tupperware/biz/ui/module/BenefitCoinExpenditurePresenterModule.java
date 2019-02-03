package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.BenefitCoinExpenditureContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/21.
 */

@Module
public class BenefitCoinExpenditurePresenterModule {
    private BenefitCoinExpenditureContract.View view;
    private PersonalDataManager mDataManager;

    public BenefitCoinExpenditurePresenterModule(BenefitCoinExpenditureContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    BenefitCoinExpenditureContract.View providerBenefitCoinExpenditureContractView(){
        return view;
    }
}