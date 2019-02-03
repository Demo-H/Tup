package com.tupperware.biz.http.api;

import com.tupperware.biz.entity.login.ForgetPwInfo;
import com.tupperware.biz.entity.login.LoginInfo;
import com.tupperware.biz.entity.login.LoginRequest;
import com.tupperware.biz.entity.login.ResetPwdRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dhunter on 2018/7/5.
 */

public interface LoginService {
    /** 登录接口 **/
    @POST("front/login/storeEmployeeLogin")
    Observable<LoginInfo> getLoginData(@Body LoginRequest loginrequest);

    /** 找回密码,获取手机号码 **/
    @GET("front/storeEmployee/getEmployeeMobile")
    Observable<ForgetPwInfo> getPhonebyStore(@Query("storeEmployeeCode") String store);

    /** 发送获取验证码 **/
    @POST("front/message/sendVerificationSms")
    Observable<ForgetPwInfo> getSMSCode(@Query("mobile") String mobile);

    /** 忘记密码,修改密码 **/
    @POST("front/storeEmployee/findPassword")
    Observable<ForgetPwInfo> forgetResetPwd(@Body ResetPwdRequest request);
}
