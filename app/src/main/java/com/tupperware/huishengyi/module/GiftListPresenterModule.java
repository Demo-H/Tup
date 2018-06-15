package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.GiftListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/7.
 */

@Module
public class GiftListPresenterModule {
    private GiftListContract.View view;
    private PersonalDataManager mDataManager;

    public GiftListPresenterModule(GiftListContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    GiftListContract.View providerGiftListContractView(){
        return view;
    }
}