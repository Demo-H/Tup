package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.login.ResponseBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.contract.PersonalSettingContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/7/5.
 */

public class PersonalSettingPresenter extends BasePresenter implements PersonalSettingContract.Presenter {

    private static final String TAG = "PersonalSettingPresenter";
    private PersonalDataManager mDataManager;
    private PersonalSettingContract.View mView;

    @Inject
    public PersonalSettingPresenter(PersonalDataManager mDataManager, PersonalSettingContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void logout() {
        addDisposabe(mDataManager.logout(new ErrorDisposableObserver<ResponseBean>() {
            @Override
            public void onNext(ResponseBean mBean) {
                //无论是否返回成功均要求退出登录
                mView.hideDialog();
//                if(mBean == null) {
//                    mView.toast("退出登录失败");
//                } else {
//                    if (mBean.isSuccess()) {
                        mView.setLogoutSuccess();
                        mDataManager.deleteSPData();
//                    } else {
//                        mView.toast(mBean.getMessage());
//                    }
//                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //无论是否返回成功均要求退出登录
                mView.hideDialog();
                mView.setLogoutSuccess();
                mDataManager.deleteSPData();
            }

            @Override
            public void onComplete() {

            }
        }));
    }
}
