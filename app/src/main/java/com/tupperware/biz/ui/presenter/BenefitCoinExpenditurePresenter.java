package com.tupperware.biz.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.BenefitCoinRequest;
import com.tupperware.biz.entity.benefit.BenefitCoinResponse;
import com.tupperware.biz.entity.benefit.CouponResponse;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.BenefitCoinExpenditureContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

import static cn.jzvd.JZUtils.TAG;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinExpenditurePresenter extends BasePresenter implements BenefitCoinExpenditureContract.Presenter {

    private PersonalDataManager mDataManager;

    private BenefitCoinExpenditureContract.View mBenefitCoinExpenditureView;

    private Activity activity;

    @Inject
    public BenefitCoinExpenditurePresenter(PersonalDataManager mDataManager, BenefitCoinExpenditureContract.View view) {
        this.mDataManager = mDataManager;
        this.mBenefitCoinExpenditureView = view;

    }

    @Override
    public void getBenefitCoinExpenditureData(int status) {
        addDisposabe(mDataManager.getBenifitCoinList(new ErrorDisposableObserver<BenefitCoinResponse>() {
            @Override
            public void onNext(BenefitCoinResponse mBean) {
                String json = ObjectUtil.jsonFormatter(mBean);
                LogF.i(TAG, json);
                mBenefitCoinExpenditureView.hideDialog();
                if(!mBean.isSuccess()) {
                    mBenefitCoinExpenditureView.toast(mBean.getMessage());
                    if(mBean.resultCode != null && (mBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || mBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mBenefitCoinExpenditureView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mBenefitCoinExpenditureView.setEmptyView();
                    }
                } else if(mBean.getPageInfo() == null){
                    mBenefitCoinExpenditureView.setEmptyView();
                } else {
                    mBenefitCoinExpenditureView.setNormalView();
                    mBenefitCoinExpenditureView.setBenefitCoinExpenditureData(mBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mBenefitCoinExpenditureView.hideDialog();
                mBenefitCoinExpenditureView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        },status, Constant.FIRST_PAGE_INDEX));
    }

    @Override
    public void getMoreBenefitCoinExpenditureData(int status, int pageIndex) {
        addDisposabe(mDataManager.getBenifitCoinList(new ErrorDisposableObserver<BenefitCoinResponse>() {
            @Override
            public void onNext(BenefitCoinResponse mbean) {
                mBenefitCoinExpenditureView.setMoreBenefitCoinExpenditureData(mbean);
            }

            @Override
            public void onComplete() {

            }
        }, status, pageIndex));
    }

    @Override
    public void getCouponListData(int statue) {
        addDisposabe(mDataManager.getCouponList(new ErrorDisposableObserver<CouponResponse>() {
            @Override
            public void onNext(CouponResponse couponResponse) {
                String json = ObjectUtil.jsonFormatter(couponResponse);
                LogF.i(TAG, json);
                mBenefitCoinExpenditureView.hideDialog();
                if(!couponResponse.isSuccess()) {
                    mBenefitCoinExpenditureView.toast(couponResponse.getMessage());
                    if(couponResponse.resultCode != null && (couponResponse.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                            || couponResponse.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mBenefitCoinExpenditureView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mBenefitCoinExpenditureView.setEmptyView();
                    }
                } else if(couponResponse.getPageInfo() == null){
                    mBenefitCoinExpenditureView.setEmptyView();
                } else {
                    mBenefitCoinExpenditureView.setNormalView();
                    mBenefitCoinExpenditureView.setCouponListData(couponResponse);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mBenefitCoinExpenditureView.hideDialog();
                mBenefitCoinExpenditureView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        }, statue, Constant.FIRST_PAGE_INDEX));
    }

    @Override
    public void getMoreCouponListData(int statue, int pageIndex) {
        addDisposabe(mDataManager.getCouponList(new ErrorDisposableObserver<CouponResponse>() {
            @Override
            public void onNext(CouponResponse couponResponse) {
//                String json = ObjectUtil.jsonFormatter(couponResponse);
//                LogF.i(TAG, json);
//                if(!couponResponse.isSuccess()) {
//                    mBenefitCoinExpenditureView.toast(couponResponse.getMessage());
//                }
                mBenefitCoinExpenditureView.setMoreCouponListData(couponResponse);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, statue, pageIndex));
    }

    private BenefitCoinRequest getRequest(int pageNo, int status) {
        BenefitCoinRequest request = new BenefitCoinRequest();
        request.setPageNo(pageNo);
        request.setPageSize(Constant.DEFAULT_MEMBER_PAGE_SIZE);
        request.setRequestDate(System.currentTimeMillis() / 1000); //传入时间戳秒
        request.setStatus(status);
        return request;
    }
}
