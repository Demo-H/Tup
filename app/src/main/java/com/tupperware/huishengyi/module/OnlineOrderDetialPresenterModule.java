package com.tupperware.huishengyi.module;

import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.ui.contract.OnlineOrderDetialContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dhunter on 2018/5/17.
 */

@Module
public class OnlineOrderDetialPresenterModule {
    private OnlineOrderDetialContract.View view;
    private OrderDataManager mDataManager;

    public OnlineOrderDetialPresenterModule(OnlineOrderDetialContract.View view, OrderDataManager mDataManager) {
        this.view = view;
        this.mDataManager = mDataManager;
    }

    @Provides
    OrderDataManager providerMainDataManager() {
        return mDataManager;
    }

    @Provides
    OnlineOrderDetialContract.View providerOnlineOrderDetialContractView(){
        return view;
    }
}