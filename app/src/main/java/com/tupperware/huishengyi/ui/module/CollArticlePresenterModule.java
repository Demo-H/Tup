package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.ui.contract.CollArticleContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/4/17.
 */

@Module
public class CollArticlePresenterModule {
    private CollArticleContract.View view;
    private CollegeDataManager mDataManager;

    public CollArticlePresenterModule(CollArticleContract.View view, CollegeDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    CollegeDataManager providerCollegeDataManager() {
        return mDataManager;
    }


    @Provides
    CollArticleContract.View providerCollArticleContractView(){
        return view;
    }
}