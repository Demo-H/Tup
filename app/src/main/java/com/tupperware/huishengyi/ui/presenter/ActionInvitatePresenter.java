package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.member.ActionMembersBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.ActionInvitateContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/14.
 */

public class ActionInvitatePresenter extends BasePresenter implements ActionInvitateContract.Presenter {

    private static final String TAG = "ActionInvitatePresenter";
    private PersonalDataManager mDataManager;
    private ActionInvitateContract.View mView;

    @Inject
    public ActionInvitatePresenter(PersonalDataManager mDataManager, ActionInvitateContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getActionInvitateData(long infoId, long storeId) {
        addDisposabe(mDataManager.getMembersbyAction(new ErrorDisposableObserver<ActionMembersBean>() {
            @Override
            public void onNext(ActionMembersBean actionMembersBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(actionMembersBean));
                mView.hideDialog();
                if (!actionMembersBean.success) {
                    mView.toast(actionMembersBean.message);
                } else if(actionMembersBean.getModels() == null || actionMembersBean.getModels().size() == 0){
                    mView.setEmptyView();
                }else {
                    mView.setActionInvitateData(actionMembersBean);
                }
            }

            @Override
            public void onComplete() {

            }
        }, infoId, storeId));
    }
}
