package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.CollegeDataManager;
import com.tupperware.huishengyi.ui.contract.WebViewContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/7/9.
 */

@Module
public class WebViewPresenterModule {
    private WebViewContract.View view;
    private CollegeDataManager mDataManager;
//    private OkHttpClientManager mOkhttpInstance;

    public WebViewPresenterModule(WebViewContract.View view, CollegeDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
//        this.mOkhttpInstance = httpclient;
    }

//    @Provides
//    OkHttpClientManager provideOkHttpClientManager() {
//        return mOkhttpInstance;
//    }

    @Provides
    CollegeDataManager providerCollegeDataManager() {
        return mDataManager;
    }

    @Provides
    WebViewContract.View providerWebViewContractView(){
        return view;
    }
}