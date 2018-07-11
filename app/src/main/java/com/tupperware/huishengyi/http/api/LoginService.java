package com.tupperware.huishengyi.http.api;

import com.tupperware.huishengyi.entity.login.ForgetPwInfo;
import com.tupperware.huishengyi.entity.login.LoginInfo;
import com.tupperware.huishengyi.entity.login.LoginRequest;
import com.tupperware.huishengyi.entity.login.ResetPwdRequest;

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
    @POST("store_api/app.php")
    Observable<LoginInfo> getLoginData(@Query("act") String act, @Body LoginRequest loginrequest);

    /** 找回密码,获取手机号码 **/
    @GET("store_api/app.php")
    Observable<ForgetPwInfo> getPhonebyStore(@Query("act") String act, @Query("storeEmployeeCode") String store);

    /** 发送获取验证码 **/
    @GET("store_api/app.php")
    Observable<ForgetPwInfo> getSMSCode(@Query("act") String act, @Query("phone") String phone);

    /** 忘记密码,修改密码 **/
    @POST("store_api/app.php")
    Observable<ForgetPwInfo> forgetResetPwd(@Query("act") String act, @Body ResetPwdRequest request);
}
