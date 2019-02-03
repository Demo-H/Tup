package com.tupperware.biz.http.api;

import com.tupperware.biz.entity.login.ResponseBean;
import com.tupperware.biz.entity.member.GiftBean;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;
import com.tupperware.biz.entity.member.RemindMemberResponse;
import com.tupperware.biz.entity.product.ProductType;
import com.tupperware.biz.entity.saleenter.MemUpgradeRequest;

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

    /** 获取会员列表 **/
    @POST("front/member/selectByCondition")
    Observable<MemberBean> getMemberList(@Body MemberSearchConditionDTO searchConditionDTO);

    @GET("front/product/findByProductType")
    Observable<ProductType> getMemberFilterProductType();

    /** 会员详情 **/
    @GET("front/member/selectByMemberDetail")
    Observable<MemberBean> getMemberDetialData(@Query("memberId") Integer memberId, @Query("mobile") String mobileNum, @Query("storeId") int storeId, @Query("employeeCode") String employeeCode);

    /** 会员礼品订单列表 **/
    @GET("front/giftOrder/getMemberGiftOrder")
    Observable<GiftBean> getGiftListData(@QueryMap Map<String, Object> maps);

    /** 发送获取验证码 **/
    @POST("front/message/sendVerificationSms")
    Observable<ResponseBean> getSMSCode(@Query("mobile") String mobile);

    @POST("front/benefit/useCouponRegister")
    Observable<ResponseBean> startRegister(@Body MemUpgradeRequest request);

    /** 净水器换滤芯提醒列表 **/
    @GET("front/store/storeFilterReservationList")
    Observable<RemindMemberResponse> getFilterReservationList(@QueryMap Map<String, Object> maps);

}
