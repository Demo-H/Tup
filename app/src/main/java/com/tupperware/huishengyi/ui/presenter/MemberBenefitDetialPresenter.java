package com.tupperware.huishengyi.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.contract.MemberBenefitDetialContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/4/2.
 */

public class MemberBenefitDetialPresenter extends BasePresenter implements MemberBenefitDetialContract.Presenter {

    private MainDataManager mDataManager;

    private MemberBenefitDetialContract.View mView;

    private Activity activity;

    @Inject
    public MemberBenefitDetialPresenter(MainDataManager mDataManager, MemberBenefitDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getMemberBenefitDetialData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<BenefitCoinExpenditureBean>() {
            @Override
            public void onNext(BenefitCoinExpenditureBean benefitCoinExpenditure) {
                mView.setMemberBenefitDetialData(benefitCoinExpenditure);
            }

            @Override
            public void onComplete() {

            }
        },BenefitCoinExpenditureBean.class, "store.txt"));
    }
}
