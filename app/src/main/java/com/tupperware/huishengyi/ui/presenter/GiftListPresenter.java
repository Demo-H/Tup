package com.tupperware.huishengyi.ui.presenter;

import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.GiftListContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/7.
 */

public class GiftListPresenter extends BasePresenter implements GiftListContract.Presenter {

    private static final String TAG = "GiftListPresenter";
    private PersonalDataManager mDataManager;
    private GiftListContract.View mView;

    @Inject
    public GiftListPresenter(PersonalDataManager mDataManager, GiftListContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getGiftListData(String memberId) {
//        addDisposabe(mDataManager.getGiftListData(new ErrorDisposableObserver<GiftBean>() {
//            @Override
//            public void onNext(GiftBean giftBean) {
//                LogF.i(TAG, ObjectUtil.jsonFormatter(giftBean));
//                mView.hideDialog();
//                if(!giftBean.success) {
//                    mView.toast(giftBean.message);
//                } else {
//                    mView.setGiftListData(giftBean);
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//                mView.hideDialog();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        }, memberId, Constant.FIRST_PAGE_INDEX));
    }

    @Override
    public void getMoreGiftListData(String memberId, int indexPage) {
//        addDisposabe(mDataManager.getGiftListData(new ErrorDisposableObserver<GiftBean>() {
//            @Override
//            public void onNext(GiftBean giftBean) {
//                mView.setMoreGiftListData(giftBean);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        }, memberId, indexPage));
    }
}
