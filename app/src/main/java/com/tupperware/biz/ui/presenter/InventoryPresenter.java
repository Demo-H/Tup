package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.hotsale.HotInventoryResponse;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.InventoryContract;
import com.tupperware.biz.utils.ObjectUtil;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/30.
 */

public class InventoryPresenter extends BasePresenter implements InventoryContract.Presenter {

    private ProductDataManager mDataManager;
    private InventoryContract.View mView;

    @Inject
    public InventoryPresenter(ProductDataManager mDataManager, InventoryContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getHotInventory(Integer storeId, String time) {
        addDisposabe(mDataManager.getHotSaleInventory(new ErrorDisposableObserver<HotInventoryResponse>() {
            @Override
            public void onNext(HotInventoryResponse bean) {
                mView.hideDialog();
                String json = ObjectUtil.jsonFormatter(bean);
                if(!bean.isSuccess()) {
                    if(bean.getMessage() != null && !bean.getMessage().isEmpty()) {
                        mView.toast(bean.getMessage());
                    }
                    if(bean.resultCode != null && (bean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                            || bean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mView.setNetErrorView();
                    }
                } else {
                    mView.setNormalView();
                    mView.setHotInventoryData(bean);
//                    if(bean.getModels() != null && bean.getModels().size() > 0) {
//                        mView.setNormalView();
//                        mView.setHotInventoryData(bean.getModels());
//                    } else {
//                        mView.setEmptyView();
//                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        }, storeId, time));
    }
}
