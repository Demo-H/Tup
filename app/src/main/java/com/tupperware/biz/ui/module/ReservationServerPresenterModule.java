package com.tupperware.biz.ui.module;

import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.ReservationServerContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/31.
 */

@Module
public class ReservationServerPresenterModule {
    private ReservationServerContract.View view;
    private PersonalDataManager mDataManager;

    public ReservationServerPresenterModule(ReservationServerContract.View view, PersonalDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    PersonalDataManager providerPersonalDataManager() {
        return mDataManager;
    }

    @Provides
    ReservationServerContract.View providerReservationServerContractView(){
        return view;
    }
}