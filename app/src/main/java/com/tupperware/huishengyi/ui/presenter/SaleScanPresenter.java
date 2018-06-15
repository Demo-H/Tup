package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.SaleScanContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/4.
 */

public class SaleScanPresenter extends BasePresenter implements SaleScanContract.Presenter {

    private static final String TAG = "SaleHandPresenter";
    private ProductDataManager mDataManager;
    private SaleScanContract.View mView;

    @Inject
    public SaleScanPresenter(ProductDataManager mDataManager, SaleScanContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getSaleScanData(String storeCode, String date, String barCode) {
        mDataManager.getSaleScanData(new ErrorDisposableObserver<SaleEnterBean>() {
            @Override
            public void onNext(SaleEnterBean saleEnterBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(saleEnterBean));
                if(!saleEnterBean.success) {
                    mView.toast(saleEnterBean.message);
                    mView.setError();
                } else {
                    mView.setSaleScanData(saleEnterBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.setError();
            }

            @Override
            public void onComplete() {

            }
        }, storeCode, date, barCode);
    }
}
