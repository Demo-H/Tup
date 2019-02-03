package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.order.OrderItemBean;
import com.tupperware.biz.http.OrderDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.OnlineOrderDetialContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

import static cn.jzvd.JZMediaManager.TAG;

/**
 * Created by dhunter on 2018/5/17.
 */

public class OnlineOrderDetialPresenter extends BasePresenter implements OnlineOrderDetialContract.Presenter {

    private OrderDataManager mDataManager;
    private OnlineOrderDetialContract.View mView;

    @Inject
    public OnlineOrderDetialPresenter(OrderDataManager mDataManager, OnlineOrderDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getOrderItemData(long id) {
        addDisposabe(mDataManager.getOrderItemData(new ErrorDisposableObserver<OrderItemBean>() {
            @Override
            public void onNext(OrderItemBean orderItemBean) {
                String json = ObjectUtil.jsonFormatter(orderItemBean);
                LogF.i(TAG, json);
                if(!orderItemBean.success) {
                    mView.toast(orderItemBean.message);
                    if(orderItemBean.resultCode != null && (orderItemBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || orderItemBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else if(orderItemBean.model == null) {
                    mView.setEmptyView();
                } else {
                    mView.setNormalView();
                    mView.setOrderItemData(orderItemBean);
                }
            }

            @Override
            public void onComplete() {

            }
        }, id));
    }
}
