package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.GiftBean;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.GiftListContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/7.
 */

public class GiftListPresenter extends BasePresenter implements GiftListContract.Presenter {

    private static final String TAG = "GiftListPresenter";
    private MemberDataManager mDataManager;
    private GiftListContract.View mView;

    @Inject
    public GiftListPresenter(MemberDataManager mDataManager, GiftListContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getGiftListData(Integer memberId) {
        addDisposabe(mDataManager.getGiftListData(new ErrorDisposableObserver<GiftBean>() {
            @Override
            public void onNext(GiftBean giftBean) {
                mView.hideDialog();
                if(!giftBean.isSuccess()) {
                    mView.toast(giftBean.message);
                    if(giftBean.resultCode != null && (giftBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                            || giftBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    } else {
                        mView.setNetErrorView();
                    }
                } else if(giftBean.getPageInfo() == null) {
                    mView.setEmptyView();
                } else {
                    mView.setGiftListData(giftBean);
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
        }, memberId, Constant.FIRST_PAGE_INDEX));
    }

    @Override
    public void getMoreGiftListData(Integer memberId, int indexPage) {
        addDisposabe(mDataManager.getGiftListData(new ErrorDisposableObserver<GiftBean>() {
            @Override
            public void onNext(GiftBean giftBean) {
                mView.setMoreGiftListData(giftBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {

            }
        }, memberId, indexPage));
    }
}
