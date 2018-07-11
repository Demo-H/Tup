package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.ui.contract.CourseDetialContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/9.
 */

@Module
public class CourseDetialPresenterModule {
    private CourseDetialContract.View view;
    private CollegeDataManager mDataManager;

    public CourseDetialPresenterModule(CourseDetialContract.View view, CollegeDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    CollegeDataManager providerCollegeDataManager() {
        return mDataManager;
    }

    @Provides
    CourseDetialContract.View providerCourseDetialContractView(){
        return view;
    }
}