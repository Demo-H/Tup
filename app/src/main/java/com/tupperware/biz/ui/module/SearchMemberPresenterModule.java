package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.contract.SearchMemberContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/30.
 */

@Module
public class SearchMemberPresenterModule {
    private SearchMemberContract.View view;
    private MemberDataManager mDataManager;

    public SearchMemberPresenterModule(SearchMemberContract.View view, MemberDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    MemberDataManager providerMemberDataManager() {
        return mDataManager;
    }

    @Provides
    SearchMemberContract.View providerSearchMemberContractView(){
        return view;
    }
}