package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.rxjava.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.member.StoreScheduleBean;
import com.tupperware.huishengyi.http.PersonalDataManager;
import com.tupperware.huishengyi.ui.contract.ActionListContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/6/7.
 */

public class ActionListPresenter extends BasePresenter implements ActionListContract.Presenter {

    private static final String TAG = "ActionListPresenter";
    private PersonalDataManager mDataManager;
    private ActionListContract.View mView;

    @Inject
    public ActionListPresenter(PersonalDataManager mDataManager, ActionListContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getScheduleListData(String storeCode) {
        addDisposabe(mDataManager.getScheduleData(new ErrorDisposableObserver<StoreScheduleBean>() {
            @Override
            public void onNext(StoreScheduleBean mBean) {
                LogF.i(TAG, ObjectUtil.jsonFormatter(mBean));
                mView.hideDialog();
                if (!mBean.success) {
                    mView.toast(mBean.message);
                } else if(mBean.getModels() == null || mBean.getModels().size() == 0){
                    mView.setEmptyView();
                }else {
                    mView.setScheduleListData(mBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
            }

            @Override
            public void onComplete() {

            }
        }, storeCode));
    }
}
