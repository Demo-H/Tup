package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.CollegeDataManager;
import com.tupperware.biz.ui.contract.CollExperienceContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/4/18.
 */

@Module
public class CollExperiencePresenterModule {
    private CollExperienceContract.View view;
    private CollegeDataManager mDataManager;

    public CollExperiencePresenterModule(CollExperienceContract.View view, CollegeDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    CollegeDataManager providerCollegeDataManager() {
        return mDataManager;
    }


    @Provides
    CollExperienceContract.View providerCollExperienceContractView(){
        return view;
    }
}