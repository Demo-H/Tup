package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.VideoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/6.
 */

@Module
public class VideoPresenterModule {
    private VideoContract.View view;
    private MainDataManager mainDataManager;

    public VideoPresenterModule(VideoContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

    @Provides
    VideoContract.View providerMainContractView(){
        return view;
    }
}
