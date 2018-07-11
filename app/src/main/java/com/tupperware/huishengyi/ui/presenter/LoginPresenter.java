package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.entity.login.LoginInfo;
import com.tupperware.huishengyi.http.LoginDataManager;
import com.tupperware.huishengyi.ui.contract.LoginContract;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/7/4.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    private static final String TAG = "LoginPresenter";
    private LoginDataManager mDataManager;
    private LoginContract.View mView;

    @Inject
    public LoginPresenter(LoginDataManager mDataManager, LoginContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void trylogin(String userName, String psw) {
        addDisposabe(mDataManager.tryLogin(new ErrorDisposableObserver<LoginInfo>() {
            @Override
            public void onNext(LoginInfo loginInfo) {
                String json = ObjectUtil.jsonFormatter(loginInfo);
                LogF.d(TAG, json);
                if(loginInfo.isSuccess() && loginInfo.getExtra() != null) {
                    String token = loginInfo.getExtra().getToken();
                    String userId = loginInfo.getModel().getpUid();
                    String storeCode = loginInfo.getExtra().getStoreCode();
                    String employeeGroup = loginInfo.getExtra().getEmployeeGroup();
                    String employeeCode = loginInfo.getExtra().getEmployeeCode();
                    String stordId = loginInfo.getExtra().getStoreId();
                    mDataManager.saveSPData(GlobalConfig.LOGIN_TOKEN, token);
//                mSharePreDate.setParam(GlobalConfig.LOGIN_TOKEN, token);
                    mDataManager.saveSPData(GlobalConfig.KEY_DATA_USERID, userId);
                    mDataManager.saveSPData(GlobalConfig.STORE_CODE, storeCode);  //店编
                    mDataManager.saveSPData(GlobalConfig.EMPLOYEE_CODE, employeeCode);
                    mDataManager.saveSPData(GlobalConfig.STORE_ID, stordId);
                    if (employeeGroup != null) {
                        mDataManager.saveSPData(GlobalConfig.EMPLOYEE_GROUP, employeeGroup);
                    } else {
                        mDataManager.saveSPData(GlobalConfig.EMPLOYEE_GROUP, "0");
                    }
                    mDataManager.saveSPData(GlobalConfig.KEY_DATA_LOGIN_INFO, json);
                    mDataManager.setHeader();
                    mView.showLoginResult(loginInfo);
                    mView.hideDialog();
                } else {
                    mView.toast(loginInfo.getMessage());
                    mView.hideDialog();
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
        }, userName, psw));
    }
}
