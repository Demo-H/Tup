package com.tupperware.biz.ui.presenter;

import android.support.v4.util.ArrayMap;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.RemindMemberResponse;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.RemindContract;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/29.
 */

public class RemindPresenter extends BasePresenter implements RemindContract.Presenter {

    private MemberDataManager mDataManager;
    private RemindContract.View mView;
    private Integer storeId;

    @Inject
    public RemindPresenter(MemberDataManager mDataManager, RemindContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getFilterReservation(long chooseTime) {
        addDisposabe(mDataManager.getFilterReservationList(new ErrorDisposableObserver<RemindMemberResponse>() {
            @Override
            public void onNext(RemindMemberResponse remindBean) {
                mView.hideDialog();
                if(remindBean != null) {
                    if(!remindBean.isSuccess()) {
                        if( remindBean.getMessage()!= null && !remindBean.getMessage().isEmpty()) {
                            mView.toast(remindBean.getMessage());
                        }
                        if(remindBean.resultCode != null && (remindBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                                || remindBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                            mView.reLogin();
                            mDataManager.deleteSPData();
                        } else {
                            mView.setNetErrorView();
                        }
                    } else if(remindBean.getPageInfo() == null || remindBean.getPageInfo().getList() == null
                            || remindBean.getPageInfo().getList().size() == 0) {
                        mView.setEmptyView();
                    } else {
                        mView.setNormalView();
                        mView.setFilterReservationData(remindBean);
                    }
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
        }, getRequestMaps(chooseTime, Constant.FIRST_PAGE_INDEX)));
    }

    @Override
    public void getMoreFilterReservation(long chooseTime, int page) {
        addDisposabe(mDataManager.getFilterReservationList(new ErrorDisposableObserver<RemindMemberResponse>() {
            @Override
            public void onNext(RemindMemberResponse remindBean) {
                if(remindBean != null) {
                    mView.setMoreFilterReservationData(remindBean);
                }
            }

            @Override
            public void onComplete() {

            }
        }, getRequestMaps(chooseTime, page)));
    }

    private Map<String, Object> getRequestMaps(long chooseTime, int page) {
        Map<String, Object> maps = new ArrayMap<>();
        storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        maps.put("storeId", storeId);
        maps.put("searchTimestamp", chooseTime);
        maps.put("page", page);
        maps.put("size", Constant.DEFAULT_MEMBER_PAGE_SIZE);
        return maps;
    }
}
