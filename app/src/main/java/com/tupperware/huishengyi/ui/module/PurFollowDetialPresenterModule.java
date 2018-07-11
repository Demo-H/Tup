package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.PurFollowDetialContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/30.
 */

@Module
public class PurFollowDetialPresenterModule {
    private PurFollowDetialContract.View view;
    private PersonalDataManager mDataManager;

    public PurFollowDetialPresenterModule(PurFollowDetialContract.View view, PersonalDataManager mainDataManager) {
        this.view = view;
        this.mDataManager = mainDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    PurFollowDetialContract.View providerPurFollowDetialContract(){
        return view;
    }
}