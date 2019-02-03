package com.tupperware.biz.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.ResOrderPendingBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.ResOrderServerContract;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by dhunter on 2018/4/8.
 */

public class ResOrderServerPresenter extends BasePresenter implements ResOrderServerContract.Presenter {

    private MainDataManager mDataManager;

    private ResOrderServerContract.View mView;

    private Activity activity;

    @Inject
    public ResOrderServerPresenter(MainDataManager mDataManager, ResOrderServerContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }


    @Override
    public void getResOrderServerData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<ResOrderPendingBean>() {
            @Override
            public void onNext(ResOrderPendingBean resOrderPendingBean) {
                mView.setResOrderServerData(resOrderPendingBean);
            }

            @Override
            public void onComplete() {

            }
        },ResOrderPendingBean.class, "order.txt"));

    }

    @Override
    public void getMoreResOrderServerData() {
        addDisposabe(mDataManager.getData(new DisposableObserver<ResOrderPendingBean>() {
            @Override
            public void onNext(ResOrderPendingBean resOrderPendingBean) {
                mView.setMoreResOrderServerData(resOrderPendingBean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },ResOrderPendingBean.class, "order.txt"));

    }
}