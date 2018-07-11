package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.BenefitCoinExpenditureContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinExpenditurePresenter extends BasePresenter implements BenefitCoinExpenditureContract.Presenter {

    private MainDataManager mDataManager;

    private BenefitCoinExpenditureContract.View mBenefitCoinExpenditureView;

    private Activity activity;

    @Inject
    public BenefitCoinExpenditurePresenter(MainDataManager mDataManager, BenefitCoinExpenditureContract.View view) {
        this.mDataManager = mDataManager;
        this.mBenefitCoinExpenditureView = view;

    }

    @Override
    public void getBenefitCoinExpenditureData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<BenefitCoinExpenditureBean>() {
            @Override
            public void onNext(BenefitCoinExpenditureBean benefitCoinExpenditure) {
                mBenefitCoinExpenditureView.setBenefitCoinExpenditureData(benefitCoinExpenditure);
            }

            @Override
            public void onComplete() {

            }
        },BenefitCoinExpenditureBean.class, "store.txt"));
    }
}
