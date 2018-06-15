package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.KeyProductRecordBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.KeyProductRecordContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/22.
 */

public class KeyProductRecordPresenter extends BasePresenter implements KeyProductRecordContract.Presenter {

    private MainDataManager mDataManager;

    private KeyProductRecordContract.View mKeyProductRecordView;

    private Activity activity;

    @Inject
    public KeyProductRecordPresenter(MainDataManager mDataManager, KeyProductRecordContract.View view) {
        this.mDataManager = mDataManager;
        this.mKeyProductRecordView = view;

    }

    @Override
    public void getKeyProductRecordData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<KeyProductRecordBean>() {
            @Override
            public void onNext(KeyProductRecordBean keyProductRecordBean) {
                mKeyProductRecordView.setKeyProductRecordData(keyProductRecordBean);
            }

            @Override
            public void onComplete() {

            }
        },KeyProductRecordBean.class, "goodsname.txt"));
    }

}
