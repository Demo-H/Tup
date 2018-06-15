package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.ui.contract.CollExperienceContract;

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