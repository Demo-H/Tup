package com.tupperware.biz.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.StoreBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.StoreSwitchContract;

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
