package com.tupperware.huishengyi.ui.module;

import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.ui.contract.ResOrderPendingContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/3/15.
 */

@Module
public class ResOrderPendingPresenterModule {
    private ResOrderPendingContract.View view;
    private OrderDataManager mDataManager;

    public ResOrderPendingPresenterModule(ResOrderPendingContract.View view, OrderDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    OrderDataManager providerMainDataManager() {
        return mDataManager;
    }

    @Provides
    ResOrderPendingContract.View providerResOrderPendingContractView(){
        return view;
    }
}