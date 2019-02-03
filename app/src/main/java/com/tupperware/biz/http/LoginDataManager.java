package com.tupperware.biz.http;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.DataManager;
import com.tupperware.biz.entity.login.ForgetPwInfo;
import com.tupperware.biz.entity.login.LoginInfo;
import com.tupperware.biz.entity.login.LoginRequest;
import com.tupperware.biz.entity.login.ResetPwdRequest;
import com.tupperware.biz.http.api.LoginService;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by dhunter on 2018/7/5.
 * 网络请求不需要token头参数的接口放于该类
 */

public class LoginDataManager extends BaseDataManager {

    public LoginDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static LoginDataManager getInstance(DataManager dataManager){
        return new LoginDataManager(dataManager);
    }

    public Disposable tryLogin(DisposableObserver<LoginInfo> consumer, String userName, String psw, int employee_group) {
        Observable observable = getService(LoginService.class).getLoginData(getLoginRequest(userName, psw, employee_group));
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 忘记密码通过员工编号获取手机号码
     * @param consumer
     * @param storeEmployeeCode
     * @return
     */
    public Disposable getPhonebyStore(DisposableObserver<ForgetPwInfo> consumer, String storeEmployeeCode) {
        Observable observable = getService(LoginService.class).getPhonebyStore(storeEmployeeCode);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 忘记密码--获取验证码
     * @param consumer
     * @param phoneNum
     * @return
     */
    public Disposable getSMSCode(DisposableObserver<ForgetPwInfo> consumer, String phoneNum) {
        Observable observable = getService(LoginService.class).getSMSCode(phoneNum);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 忘记密码--设置新密码
     * @param consumer
     * @param request
     * @return
     */
    public Disposable forgetResetPwd(DisposableObserver<ForgetPwInfo> consumer, ResetPwdRequest request) {
        Observable observable = getService(LoginService.class).forgetResetPwd(request);
        return changeIOToMainThread(observable, consumer);
    }

    private LoginRequest getLoginRequest(String userName, String psw, int employee_group) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setClientId(userName);
        loginRequest.setPwd(psw);
        loginRequest.setPlatform(GlobalConfig.PLATFORM);
        loginRequest.setStoreEmployeeGroup(employee_group);
        return loginRequest;
    }
}
