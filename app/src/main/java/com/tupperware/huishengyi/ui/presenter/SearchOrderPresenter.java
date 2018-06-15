package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.order.OrderBean;
import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.ui.contract.SearchOrderContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

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