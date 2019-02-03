package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.order.OrderBean;
import com.tupperware.biz.http.OrderDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.ResOrderPendingContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

import static cn.jzvd.JZUtils.TAG;

/**
 * Created by dhunter on 2018/3/15.
 */

public class ResOrderPendingPresenter extends BasePresenter implements ResOrderPendingContract.Presenter {

    private OrderDataManager mDataManager;

    private ResOrderPendingContract.View mResOrderPendingView;


    @Inject
    public ResOrderPendingPresenter(OrderDataManager mDataManager, ResOrderPendingContract.View view) {
        this.mDataManager = mDataManager;
        this.mResOrderPendingView = view;

    }


    @Override
    public void getResOrderPendingData(String code, String status) {
        addDisposabe(mDataManager.getOrderData(new ErrorDisposableObserver<OrderBean>() {
            @Override
            public void onNext(OrderBean orderBean) {
                String json = ObjectUtil.jsonFormatter(orderBean);
                LogF.i(TAG, json);
                if(!orderBean.success) {
                    mResOrderPendingView.toast(orderBean.message);
                    if(orderBean.resultCode != null && (orderBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || orderBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mResOrderPendingView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else if(orderBean.models == null || orderBean.models.isEmpty()) {
                    mResOrderPendingView.setEmptyView();
                } else {
                    mResOrderPendingView.setNormalView();
                    mResOrderPendingView.setResOrderPendingData(orderBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mResOrderPendingView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        }, code, status));

    }

    @Override
    public void getMoreResOrderPendingData(String code, String status, int pageIndex) {
        addDisposabe(mDataManager.getMoreOrderData(new DisposableObserver<OrderBean>() {
            @Override
            public void onNext(OrderBean orderBean) {
                String json = ObjectUtil.jsonFormatter(orderBean);
                LogF.i(TAG, json);
                if(!orderBean.success) {
                    mResOrderPendingView.toast(orderBean.message);
                } else {
                    mResOrderPendingView.setMoreResOrderPendingData(orderBean);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },code, status, pageIndex));

    }
}
