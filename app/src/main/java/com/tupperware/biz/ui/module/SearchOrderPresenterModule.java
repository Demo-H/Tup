package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.OrderDataManager;
import com.tupperware.biz.ui.contract.SearchOrderContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/30.
 */

@Module
public class SearchOrderPresenterModule {
    private SearchOrderContract.View view;
    private OrderDataManager mDataManager;

    public SearchOrderPresenterModule(SearchOrderContract.View view, OrderDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    OrderDataManager providerMainDataManager() {
        return mDataManager;
    }

    @Provides
    SearchOrderContract.View providerSearchOrderContractView(){
        return view;
    }
}