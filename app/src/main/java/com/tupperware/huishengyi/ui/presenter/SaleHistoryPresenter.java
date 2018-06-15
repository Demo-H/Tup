package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.SaleHistoryContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/28.
 */

public class SaleHistoryPresenter extends BasePresenter implements SaleHistoryContract.Presenter {

    private static final String TAG = "SaleHistoryPresenter";
    private ProductDataManager mDataManager;
    private SaleHistoryContract.View mView;

    @Inject
    public SaleHistoryPresenter(ProductDataManager mDataManager, SaleHistoryContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getSaleHistoryData(String storeCode, String date) {
        addDisposabe(mDataManager.getSaleHistoryData(new ErrorDisposableObserver<SaleEnterBean>() {
            @Override
            public void onNext(SaleEnterBean saleEnterBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(saleEnterBean));
                mView.hideDialog();
                if(!saleEnterBean.success) {
                    mView.toast(saleEnterBean.message);
                    mView.setNetErrorView();
                } else if(saleEnterBean.models == null || saleEnterBean.models.size() == 0) {
                    mView.setEmptyView();
                } else {
                    mView.setNormalView();
                    mView.setSaleHistoryData(saleEnterBean);
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
        }, storeCode, date));
    }

//    @Override
//    public void getMoreSaleHistoryData() {
//
//    }
}
