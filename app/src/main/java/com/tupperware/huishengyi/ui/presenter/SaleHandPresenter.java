package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.saleenter.SaleTabBean;
import com.tupperware.huishengyi.http.ProductDataManager;
import com.tupperware.huishengyi.ui.contract.SaleHandContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

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
                    mView.setNetErrorView();
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
