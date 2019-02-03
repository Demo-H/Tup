package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.order.OrderBean;
import com.tupperware.biz.http.OrderDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.SearchOrderContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/30.
 */

public class SearchOrderPresenter extends BasePresenter implements SearchOrderContract.Presenter {
    private static final String TAG = "SearchPresenter";

    private OrderDataManager mDataManager;
    private SearchOrderContract.View mView;

    @Inject
    public SearchOrderPresenter(OrderDataManager mDataManager, SearchOrderContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getOrderSearchData(String code, String orderCode) {
        addDisposabe(mDataManager.getOrderSearchData(new ErrorDisposableObserver<OrderBean>() {
            @Override
            public void onNext(OrderBean orderBean) {
                String json = ObjectUtil.jsonFormatter(orderBean);
                LogF.i(TAG, json);
                mView.hideDialog();
                if(!orderBean.success) {
                    mView.toast(orderBean.message);
                    if(orderBean.resultCode != null && (orderBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || orderBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else if(orderBean.models == null || orderBean.models.isEmpty()) {
                    mView.setEmptyView();
                } else {
                    mView.setNormalView();
                    mView.setOrderSearchData(orderBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
            }

            @Override
            public void onComplete() {

            }
        }, code, orderCode));
    }

}