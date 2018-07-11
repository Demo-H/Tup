package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.GiftBean;
import com.tupperware.huishengyi.http.MemberDataManager;
import com.tupperware.huishengyi.ui.contract.GiftListContract;

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
    public void getGiftListData(String memberId) {
        addDisposabe(mDataManager.getGiftListData(new ErrorDisposableObserver<GiftBean>() {
            @Override
            public void onNext(GiftBean giftBean) {
                mView.hideDialog();
                if(!giftBean.isSuccess()) {
                    mView.toast(giftBean.message);
                    mView.setNetErrorView();
                } else if(giftBean.getModels() == null || giftBean.getModels().size() == 0) {
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
    public void getMoreGiftListData(String memberId, int indexPage) {
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
