package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.CollegeDataManager;
import com.tupperware.biz.ui.contract.BusinessCollegeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/2.
 */

@Module
public class BusinessCollegePresenterModule {
    private BusinessCollegeContract.View view;
    private CollegeDataManager mDataManager;

    public BusinessCollegePresenterModule(BusinessCollegeContract.View view, CollegeDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    CollegeDataManager providerCollegeDataManager() {
        return mDataManager;
    }

    @Provides
    BusinessCollegeContract.View providerBusinessCollegeContractView(){
        return view;
    }
}