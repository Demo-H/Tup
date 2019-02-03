package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.msg.MsgBean;
import com.tupperware.biz.http.MainDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.MessageContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.logutils.LogF;

import javax.inject.Inject;


/**
 * Created by dhunter on 2018/5/22.
 */

public class MessagePresenter extends BasePresenter implements MessageContract.Presenter {
    private static final String TAG = "MessagePresenter";

    private MainDataManager mDataManager;
    private MessageContract.View mView;

    @Inject
    public MessagePresenter(MainDataManager mDataManager, MessageContract.View mView) {
        this.mDataManager = mDataManager;
        this.mView = mView;
    }

    @Override
    public void getMsgData() {
        addDisposabe(mDataManager.getMsgData(new ErrorDisposableObserver<MsgBean>() {
            @Override
            public void onNext(MsgBean msgBean) {
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
                    mView.setMsgData(msgBean);
                }
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
