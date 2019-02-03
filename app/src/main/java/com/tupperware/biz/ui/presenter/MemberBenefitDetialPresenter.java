package com.tupperware.biz.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.benefit.CouponResponse;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.ui.contract.MemberBenefitDetialContract;

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
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<CouponResponse>() {
            @Override
            public void onNext(CouponResponse benefitCoinExpenditure) {
                mView.setMemberBenefitDetialData(benefitCoinExpenditure);
            }

            @Override
            public void onComplete() {

            }
        },CouponResponse.class, "store.txt"));
    }
}
