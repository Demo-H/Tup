package com.tupperware.biz.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.benefit.CouponResponse;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.BenefitCoinDeadlineContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/4/2.
 */

public class BenefitCoinDeadlinePresenter extends BasePresenter implements BenefitCoinDeadlineContract.Presenter {

    private MainDataManager mDataManager;

    private BenefitCoinDeadlineContract.View mView;

    private Activity activity;

    @Inject
    public BenefitCoinDeadlinePresenter(MainDataManager mDataManager, BenefitCoinDeadlineContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getBenefitCoinDeadlineData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<CouponResponse>() {
            @Override
            public void onNext(CouponResponse benefitCoinExpenditure) {
                mView.setBenefitCoinDeadlineData(benefitCoinExpenditure);
            }

            @Override
            public void onComplete() {

            }
        },CouponResponse.class, "store.txt"));
    }
}
