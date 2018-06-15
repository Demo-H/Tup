package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.SaleEnterClassifyContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/28.
 */

public class SaleEnterClassifyPresenter extends BasePresenter implements SaleEnterClassifyContract.Presenter {

    private static final String TAG = "SaleEnterClassifyPresenter";
    private ProductDataManager mDataManager;
    private SaleEnterClassifyContract.View mView;

    @Inject
    public SaleEnterClassifyPresenter(ProductDataManager mDataManager, SaleEnterClassifyContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getSaleEnterData(String storeCode, String date, String seriesId) {
        addDisposabe(mDataManager.getSaleEnterData(new ErrorDisposableObserver<SaleEnterBean>() {
            @Override
            public void onNext(SaleEnterBean saleEnterBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(saleEnterBean));
                if(!saleEnterBean.success) {
                    mView.toast(saleEnterBean.message);
                    mView.setNetErrorView();
                } else if(saleEnterBean.models == null || saleEnterBean.models.size() == 0) {
                    mView.setEmptyView();
                } else {
                    mView.setNormalView();
                    mView.setSaleEnterData(saleEnterBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        }, storeCode, date, seriesId));
    }

//    @Override
//    public void getMoreSaleEnterData() {
//
//    }
}
