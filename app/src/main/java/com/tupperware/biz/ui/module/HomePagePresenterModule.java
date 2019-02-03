package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.HomePageContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/12.
 */

@Module
public class HomePagePresenterModule {
    private HomePageContract.View view;
//    private MainDataManager mainDataManager;
    private PersonalDataManager mDataManager;

    public HomePagePresenterModule(HomePageContract.View view, PersonalDataManager mainDataManager) {
        this.view = view;
        this.mDataManager = mainDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    HomePageContract.View providerMainContractView(){
        return view;
    }
}