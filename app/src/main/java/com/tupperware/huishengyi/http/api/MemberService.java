package com.tupperware.huishengyi.http.api;

import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.entity.member.GiftBean;
import com.tupperware.huishengyi.entity.member.MemberBean;
import com.tupperware.huishengyi.entity.saleenter.MemUpgradeRequest;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dhunter on 2018/7/6.
 */

public interface MemberService {

    /** 搜索获取会员列表 **/
    @GET("store_api/app.php")
    Observable<MemberBean> getMemberSearchData(@Query("act") String act, @QueryMap Map<String, Object> maps);

    /** 搜索获取会员列表 **/
    @GET("store_api/app.php")
    Observable<MemberBean> getMemberDetialData(@Query("act") String act, @Query("mobile") String mobileNum);

    /** 会员礼品订单列表 **/
    @GET("store_api/Store.php")
    Observable<GiftBean> getGiftListData(@Query("act") String act, @QueryMap Map<String, Object> maps);

    /** 发送获取验证码 **/
    @GET("store_api/app.php")
    Observable<ResponseBean> getSMSCode(@Query("act") String act, @Query("phone") String phone);

    @POST("store_api/app.php")
    Observable<ResponseBean> startRegister(@Query("act") String act, @Body MemUpgradeRequest request);
}
