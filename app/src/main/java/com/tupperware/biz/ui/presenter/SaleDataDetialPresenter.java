package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.saleenter.SaleReportBean;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.SaleDataDetialContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/11.
 */

public class SaleDataDetialPresenter extends BasePresenter implements SaleDataDetialContract.Presenter {

    private static final String TAG = "SaleDataDetialPresenter";
    private ProductDataManager mDataManager;
    private SaleDataDetialContract.View mView;

    @Inject
    public SaleDataDetialPresenter(ProductDataManager mDataManager, SaleDataDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getSaleReportData(String storeCode, int type) {
        addDisposabe(mDataManager.getSaleReportData(new ErrorDisposableObserver<SaleReportBean>() {
            @Override
            public void onNext(SaleReportBean saleReportBean) {
                mView.hideDialog();
                LogF.i(TAG, ObjectUtil.jsonFormatter(saleReportBean));
                if(!saleReportBean.success) {
                    mView.toast(saleReportBean.message);
                    if(saleReportBean.resultCode != null && (saleReportBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || saleReportBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else {
                    mView.setSaleReportData(saleReportBean);
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
        }, storeCode, type));

    }
}
