package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.StoreBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.StoreSwitchContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/19.
 */

public class StoreSwitchPresenter extends BasePresenter implements StoreSwitchContract.Presenter {

    private MainDataManager mDataManager;

    private StoreSwitchContract.View mStoreSwitchView;

    private Activity activity;


    @Inject
    public StoreSwitchPresenter(MainDataManager mDataManager, StoreSwitchContract.View view) {
        this.mDataManager = mDataManager;
        this.mStoreSwitchView = view;

    }




    @Override
    public void getStoreData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<StoreBean>() {
            @Override
            public void onNext(StoreBean storeBean) {
                mStoreSwitchView.setStoreData(storeBean);
            }

            @Override
            public void onComplete() {

            }
        },StoreBean.class, "store.txt"));
    }

    @Override
    public void getRefreshStoreData() {
    }
}
