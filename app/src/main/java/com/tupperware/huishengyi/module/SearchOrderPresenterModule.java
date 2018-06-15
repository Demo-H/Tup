package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.ui.contract.SearchOrderContract;

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