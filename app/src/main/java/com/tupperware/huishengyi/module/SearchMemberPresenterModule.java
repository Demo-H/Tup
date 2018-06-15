package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.ui.contract.SearchMemberContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/30.
 */

@Module
public class SearchMemberPresenterModule {
    private SearchMemberContract.View view;
    private OrderDataManager mDataManager;

    public SearchMemberPresenterModule(SearchMemberContract.View view, OrderDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    OrderDataManager providerMainDataManager() {
        return mDataManager;
    }

    @Provides
    SearchMemberContract.View providerSearchMemberContractView(){
        return view;
    }
}