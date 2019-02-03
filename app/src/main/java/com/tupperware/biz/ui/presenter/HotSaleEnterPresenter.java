package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.BaseResponse;
import com.tupperware.biz.entity.hotsale.HotInventoryResponse;
import com.tupperware.biz.entity.hotsale.HotSaleEnterReqeust;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.HotSaleEnterContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/12/3.
 */

public class HotSaleEnterPresenter  extends BasePresenter implements HotSaleEnterContract.Presenter {

    private ProductDataManager mDataManager;
    private HotSaleEnterContract.View mView;

    @Inject
    public HotSaleEnterPresenter(ProductDataManager mDataManager, HotSaleEnterContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getHotEnterList(Integer storeId) {
        addDisposabe(mDataManager.getHotEnterList(new ErrorDisposableObserver<HotInventoryResponse>() {
            @Override
            public void onNext(HotInventoryResponse bean) {
                mView.hideDialog();
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
                    if(bean.getModels() != null && bean.getModels().size() > 0) {
                        mView.setNormalView();
                        mView.setHotSaleListData(bean.getModels());
                    } else {
                        mView.setEmptyView();
                    }
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
        }, storeId));
    }

    @Override
    public void submitHotSale(HotSaleEnterReqeust requestData) {
        addDisposabe(mDataManager.submitHotSale(new ErrorDisposableObserver<BaseResponse>() {
            @Override
            public void onNext(BaseResponse response) {
                mView.hideDialog();
                if(!response.isSuccess()) {
                    if(response.getMessage() != null && !response.getMessage().isEmpty()) {
                        mView.toast(response.getMessage());
                    } else {
                        mView.toast("网络异常，请重新提交");
                    }
                    if(response.resultCode != null && (response.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                            || response.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    mView.setSubmitResult();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.toast("网络错误，请重试");
            }

            @Override
            public void onComplete() {

            }
        }, requestData));
    }
}
