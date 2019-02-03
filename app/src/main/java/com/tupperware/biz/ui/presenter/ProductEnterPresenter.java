package com.tupperware.biz.ui.presenter;


import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.ProductEnterBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.ProductEnterContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/23.
 */

public class ProductEnterPresenter extends BasePresenter implements ProductEnterContract.Presenter {

    private MainDataManager mDataManager;

    private ProductEnterContract.View mProductEnterView;

    private Activity activity;

    @Inject
    public ProductEnterPresenter(MainDataManager mDataManager, ProductEnterContract.View view) {
        this.mDataManager = mDataManager;
        this.mProductEnterView = view;

    }

    @Override
    public void getProductEnterData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<ProductEnterBean>() {
            @Override
            public void onNext(ProductEnterBean productEnterBean) {
                mProductEnterView.setProductEnterData(productEnterBean);
            }

            @Override
            public void onComplete() {

            }
        },ProductEnterBean.class, "goodsname.txt"));
    }
}
