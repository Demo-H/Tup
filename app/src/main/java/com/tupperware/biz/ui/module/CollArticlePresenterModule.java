package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.CollegeDataManager;
import com.tupperware.biz.ui.contract.CollArticleContract;

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