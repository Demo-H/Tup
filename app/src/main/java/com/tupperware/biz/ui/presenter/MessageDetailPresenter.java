package com.tupperware.biz.ui.presenter;

import android.app.Activity;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.msg.MsgItemBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.MessageDetialContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;

import static cn.jzvd.JZUtils.TAG;

/**
 * Created by dhunter on 2018/3/26.
 */

public class MessageDetailPresenter extends BasePresenter implements MessageDetialContract.Presenter {

    private MainDataManager mDataManager;

    private MessageDetialContract.View mView;

    private Activity activity;

    private long mTimestamp = 0;

    @Inject
    public MessageDetailPresenter(MainDataManager mDataManager, MessageDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;

    }

    @Override
    public void getMessageDetialData(long msgTagId) {
        addDisposabe(mDataManager.getMsgItemData(new ErrorDisposableObserver<MsgItemBean>() {
            @Override
            public void onNext(MsgItemBean msgBean) {
                String json = ObjectUtil.jsonFormatter(msgBean);
                LogF.i(TAG, json);
                if(!msgBean.success) {
                    mView.toast(msgBean.message);
                    if(msgBean.resultCode != null && (msgBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                    || msgBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                        mView.reLogin();
                        mDataManager.deleteSPData();
                    }
                } else if(msgBean.models == null || msgBean.models.isEmpty()) {
                    mView.setEmptyView();
                } else {
                    mView.setNormalView();
                    mView.setMessageDetialData(msgBean);
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
        },msgTagId, mTimestamp));
    }

    @Override
    public void getMoreMessageDetialData(long msgTagId, long timestamp) {
        addDisposabe(mDataManager.getMsgItemData(new ErrorDisposableObserver<MsgItemBean>() {
            @Override
            public void onNext(MsgItemBean msgBean) {
                String json = ObjectUtil.jsonFormatter(msgBean);
                LogF.i(TAG, json);
                if(!msgBean.success) {
                    mView.toast(msgBean.message);
                } else {
                    mView.setMoreMessageDetialData(msgBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {

            }
        },msgTagId, timestamp));
    }
}
