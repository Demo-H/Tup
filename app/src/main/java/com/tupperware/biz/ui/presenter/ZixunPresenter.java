package com.tupperware.biz.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.ZixunBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.ZixunContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/5.
 */

public class ZixunPresenter extends BasePresenter implements ZixunContract.Presenter {

    private MainDataManager mDataManager;

    private ZixunContract.View mZixunView;

    private Activity activity;

    @Inject
    public ZixunPresenter(MainDataManager mDataManager, ZixunContract.View view) {
        this.mDataManager = mDataManager;
        this.mZixunView = view;

    }

    @Override
    public void getZixunData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<ZixunBean>() {
            @Override
            public void onNext(ZixunBean zixunBean) {
                mZixunView.setZixunData(zixunBean);
            }

            @Override
            public void onComplete() {

            }
        },ZixunBean.class, "find.txt"));
    }

    @Override
    public void getMoreZixunData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<ZixunBean>() {
            @Override
            public void onNext(ZixunBean zixunBean) {
                mZixunView.setMoreZixunData(zixunBean);
            }

            @Override
            public void onComplete() {

            }
        },ZixunBean.class, "find.txt"));
    }

}
