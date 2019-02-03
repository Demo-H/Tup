package com.tupperware.biz.http.api;

import com.tupperware.biz.entity.BaseResponse;
import com.tupperware.biz.entity.MeBenfitCoin;
import com.tupperware.biz.entity.PurFollowDetialBean;
import com.tupperware.biz.entity.StaffManagerBean;
import com.tupperware.biz.entity.StaffRequest;
import com.tupperware.biz.entity.benefit.BenefitCoinResponse;
import com.tupperware.biz.entity.benefit.CouponResponse;
import com.tupperware.biz.entity.college.ConditionRequest;
import com.tupperware.biz.entity.login.ModifiedPwdRequest;
import com.tupperware.biz.entity.login.ResponseBean;
import com.tupperware.biz.entity.member.ActionMembersBean;
import com.tupperware.biz.entity.member.DevMemberRequest;
import com.tupperware.biz.entity.member.DevMemberRespone;
import com.tupperware.biz.entity.member.MemberAddBean;
import com.tupperware.biz.entity.member.MemberReportBean;
import com.tupperware.biz.entity.member.PersonalQrBean;
import com.tupperware.biz.entity.member.RemarksRequest;
import com.tupperware.biz.entity.member.ReservationServerBean;
import com.tupperware.biz.entity.member.SendMessageRequest;
import com.tupperware.biz.entity.member.StoreScheduleBean;
import com.tupperware.biz.entity.saleenter.ResponeBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dhunter on 2018/6/4.
 */

public interface PersonalService {

    /**
     * 退出登录
     * @return
     */
    @POST("front/storeEmployee/storeEmployeeLogOut")
    Observable<ResponseBean> logout();


    @GET("front/integralAccount/findIntegralAndCoupon")
    Observable<MeBenfitCoin> getBenifitCoinNum(@Query("storeId") Integer storeId);


    @POST("front/storeEmployee/updatePassword")
    Observable<ResponseBean> modifiedPwd(@Body ModifiedPwdRequest request);


    /**
     * 惠金币列表
     * @return
     */
    @GET("front/integralAccount/storeIntegral")
    Observable<BenefitCoinResponse> getBenifitCoinList(@QueryMap Map<String, Object> maps);

    /**
     * 优惠券列表
     * @return
     */
    @GET("front/benefit/getList")
    Observable<CouponResponse> getCouponList(@QueryMap Map<String, Object> maps);

    /**
     * 获取个人微店
     * @param storeCode
     * @return
     */
    @GET("front/pos/order/order/logo/{storeCode}")
    Observable<PersonalQrBean> getPersonalQrData(@Path("storeCode") String storeCode);

    /**
     * 提交发展新会员数据
     * @return
     */
    @POST("front/member/expand/")
    Observable<DevMemberRespone> getDevMemberData(@Body DevMemberRequest devMemberRequest);

    /**
     * 发送微信公众号消息模板
     */
    @POST("front/store/sendWelcomeMessage")
    Observable<BaseResponse> sendMessageToWechat(@Body SendMessageRequest requestData);

    /**
     * 提交会员备注
     * @return
     */
    @POST("front/member/setMemberRemark/")
    Observable<ResponeBean> submitRemarks(@Body RemarksRequest remarksRequest);

    /**
     * 在会员详情中获取会员参加预约活动的列表
     * @param conditionRequest
     * @return
     */
    @POST("front/pos/prom/member/enroll/")
    Observable<ReservationServerBean> getReservationServerData(@Body ConditionRequest conditionRequest);



    /**
     * 根据门店获取待办事项
     * @param storeCode
     * @return
     */
    @GET("front/pos/prom/store/{storeCode}")
    Observable<StoreScheduleBean> getScheduleData(@Path("storeCode") String storeCode);

    /**
     * 根据活动查询参与活动的人【爱会员】
     * @param infoId
     * @param storeId
     * @return
     */
    @GET("front/pos/prom/getEnroll/{infoId}/{storeId}")
    Observable<ActionMembersBean> getMembersbyAction(@Path("infoId") long infoId, @Path("storeId") long storeId);

    /**
     * 参与活动人详情【爱会员】
     * @param id
     * @return
     */
    @GET("front/pos/prom/enrollInfo/{id}")
    Observable<ActionMembersBean> getActionMemberDetial(@Path("id") long id);

    /**
     * 获取会员数据
     * @param storeId
     * @return
     */
    @GET("front/member/getStatisticsNew/")
    Observable<MemberReportBean> getMemberReportData(@Query("storeId") Integer storeId);

    /**
     * 获取今日新增
     * @param storeId
     * @return
     */
    @GET("front/member/getStatisticsMemberDailyNew/")
    Observable<MemberAddBean> getTodayNewAddData(@Query("storeId") Integer storeId);


    @POST("front/member/getByTags/")
    Observable<PurFollowDetialBean> getPurFollowMemberData(@Query("page") int page, @Query("size") int size, @Body ConditionRequest conditionRequest);

    /**
     * 获取店员列表
     * @param storeId
     * @return
     */
    @GET("front/storeEmployee/findByStoreId")
    Observable<StaffManagerBean> getStaffListData(@Query("storeId") Integer storeId);

    /**
     * 添加店员
     * @param reqData
     * @return
     */
    @POST("front/storeEmployee/addStoreEmployee")
    Observable<BaseResponse> addStaffData(@Body StaffRequest reqData);

    /**
     * 修改店员
     * @param reqData
     * @return
     */
    @POST("front/storeEmployee/updateStoreEmployee")
    Observable<BaseResponse> updateStaffData(@Body StaffRequest reqData);
}
