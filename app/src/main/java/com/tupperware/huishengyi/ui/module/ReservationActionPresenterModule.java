package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.ReservationActionContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/6/14.
 */

@Module
public class ReservationActionPresenterModule {
    private ReservationActionContract.View view;
    private PersonalDataManager mDataManager;

    public ReservationActionPresenterModule(ReservationActionContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    ReservationActionContract.View providerReservationActionContractView(){
        return view;
    }
}