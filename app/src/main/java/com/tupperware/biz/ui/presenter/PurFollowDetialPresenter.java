package com.tupperware.biz.ui.presenter;


import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.PurFollowDetialBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.PurFollowDetialContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by dhunter on 2018/3/30.
 */

public class PurFollowDetialPresenter extends BasePresenter implements PurFollowDetialContract.Presenter {

    private static final String TAG = "PurFollowDetialPresenter";
    private PersonalDataManager mDataManager;
    private PurFollowDetialContract.View mView;

    @Inject
    public PurFollowDetialPresenter(PersonalDataManager mDataManager, PurFollowDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }


    @Override
    public void getPurFollowDetialData(String tagCodes, Integer currentStoreId) {
        addDisposabe(mDataManager.getPurFollowMemberData(new ErrorDisposableObserver<PurFollowDetialBean>() {
            @Override
            public void onNext(PurFollowDetialBean mBean) {
                String json = ObjectUtil.jsonFormatter(mBean);
                LogF.i(TAG, json);
                if(!mBean.isSuccess()) {
                    mView.toast(mBean.getMessage() + "");
                    if(mBean.resultCode != null && (mBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || mBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else if(mBean.getPageInfo() == null || mBean.getPageInfo().getList() == null || mBean.getPageInfo().getList().isEmpty()) {
                    mView.setEmptyView();
                } else {
                    mView.setNormalView();
                    mView.setPurFollowDetialData(mBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        }, tagCodes, currentStoreId, Constant.FIRST_PAGE_INDEX));
    }

    @Override
    public void getMorePurFollowDetialData(String tagCodes, Integer currentStoreId, int pageIndex) {
        addDisposabe(mDataManager.getPurFollowMemberData(new DisposableObserver<PurFollowDetialBean>() {
            @Override
            public void onNext(PurFollowDetialBean mBean) {
                String json = ObjectUtil.jsonFormatter(mBean);
                LogF.i(TAG, json);
                if(!mBean.isSuccess()) {
                    mView.toast(mBean.getMessage());
                } else {
                    mView.setMorePurFollowDetialData(mBean);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },tagCodes, currentStoreId, pageIndex));
    }
}
