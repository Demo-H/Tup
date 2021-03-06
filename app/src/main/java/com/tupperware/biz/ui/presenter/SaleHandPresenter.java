package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.saleenter.SaleTabBean;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.SaleHandContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/25.
 */

public class SaleHandPresenter extends BasePresenter implements SaleHandContract.Presenter{

    private static final String TAG = "SaleHandPresenter";
    private ProductDataManager mDataManager;
    private SaleHandContract.View mView;

    @Inject
    public SaleHandPresenter(ProductDataManager mDataManager, SaleHandContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getLableData() {
        mDataManager.getSaleTabData(new ErrorDisposableObserver<SaleTabBean>() {
            @Override
            public void onNext(SaleTabBean saleTabBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(saleTabBean));
                if(!saleTabBean.success) {
                    mView.toast(saleTabBean.message);
                    if(saleTabBean.resultCode != null && (saleTabBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || saleTabBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mView.setNetErrorView();
                    }
                } else {
                    mView.setNormalView();
                    mView.setLableData(saleTabBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
//                mView.setTestData();
                mView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
