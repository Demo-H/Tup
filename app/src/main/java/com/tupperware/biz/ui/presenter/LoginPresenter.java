package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.login.LoginInfo;
import com.tupperware.biz.http.LoginDataManager;
import com.tupperware.biz.ui.contract.LoginContract;
import com.tupperware.biz.utils.ObjectUtil;
import com.tupperware.biz.utils.StringUtils;
import com.tupperware.biz.utils.logutils.LogF;

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
    public void trylogin(final String userName, String psw, int employee_group) {
        addDisposabe(mDataManager.tryLogin(new ErrorDisposableObserver<LoginInfo>() {
            @Override
            public void onNext(LoginInfo loginInfo) {
                String json = ObjectUtil.jsonFormatter(loginInfo);
                LogF.d(TAG, json);
                if(loginInfo.isSuccess() && loginInfo.getExtra() != null) {
                    String token = loginInfo.getExtra().getToken();
                    String storeCode = loginInfo.getExtra().getStoreCode();
                    int employeeGroup = loginInfo.getExtra().getEmployeeGroup();
                    String loginId = loginInfo.getExtra().getLoginId();
                    String employeeCode = loginInfo.getExtra().getEmployeeCode(); //店员登录的时候店员的ID
                    Integer stordId = loginInfo.getExtra().getStoreId();
                    mDataManager.saveSPData(GlobalConfig.LOGIN_TOKEN, token);
                    if(StringUtils.StringChangeToInt(employeeCode) == 0) {
                        mDataManager.saveSPData(GlobalConfig.LOGIN_ID, loginId);  //登录账户，可能是店员编号或门店编号
                    }
                    mDataManager.saveSPData(GlobalConfig.KEY_DATA_USERID, storeCode); //userId和店编一致
                    mDataManager.saveSPData(GlobalConfig.LOGIN_ID, loginId);  //登录账户，可能是店员编号或门店编号
                    mDataManager.saveSPData(GlobalConfig.STORE_CODE, storeCode);  //店编
                    mDataManager.saveSPData(GlobalConfig.EMPLOYEE_CODE, employeeCode); //旧店编
                    mDataManager.saveSPObjectData(GlobalConfig.STORE_ID, stordId);  //对应门店ID
                    if (employeeGroup == 0) {
                        mDataManager.saveSPData(GlobalConfig.EMPLOYEE_GROUP, "0");
                    } else {
                        mDataManager.saveSPData(GlobalConfig.EMPLOYEE_GROUP, "1");
                    }
                    mDataManager.saveSPData(GlobalConfig.KEY_DATA_LOGIN_INFO, json);
                    mDataManager.saveSPObjectData(GlobalConfig.LAST_LOGIN_ACCOUNT, userName, GlobalConfig.SHARE_PREFERENCE_SETTING);
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
        }, userName, psw, employee_group));
    }
}
