package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.CollegeDataManager;
import com.tupperware.biz.ui.contract.CollCourseContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/4/17.
 */

@Module
public class CollCoursePresenterModule {
    private CollCourseContract.View view;
    private CollegeDataManager mDataManager;

    public CollCoursePresenterModule(CollCourseContract.View view, CollegeDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    CollegeDataManager providerCollegeDataManager() {
        return mDataManager;
    }

    @Provides
    CollCourseContract.View providerCollCourseContractView(){
        return view;
    }
}