package com.tupperware.huishengyi.http.api;

import com.tupperware.huishengyi.entity.PurFollowDetialBean;
import com.tupperware.huishengyi.entity.college.ConditionRequest;
import com.tupperware.huishengyi.entity.login.ModifiedPwdRequest;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.entity.member.ActionMembersBean;
import com.tupperware.huishengyi.entity.member.DevMemberRequest;
import com.tupperware.huishengyi.entity.member.DevMemberRespone;
import com.tupperware.huishengyi.entity.member.MemberAddBean;
import com.tupperware.huishengyi.entity.member.MemberReportBean;
import com.tupperware.huishengyi.entity.member.PersonalQrBean;
import com.tupperware.huishengyi.entity.member.RemarksRequest;
import com.tupperware.huishengyi.entity.member.ReservationServerBean;
import com.tupperware.huishengyi.entity.member.StoreScheduleBean;
import com.tupperware.huishengyi.entity.saleenter.ResponeBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dhunter on 2018/6/4.
 */

public interface PersonalService {

    /**
     * 退出登录
     * @param act
     * @return
     */
    @GET("store_api/app.php")
    Observable<ResponseBean> logout(@Query("act") String act);

    @POST("store_api/app.php")
    Observable<ResponseBean> modifiedPwd(@Query("act") String act, @Body ModifiedPwdRequest request);

    /**
     * 获取个人微店
     * @param storeCode
     * @return
     */
    @GET("front/order/order/logo/{storeCode}")
    Observable<PersonalQrBean> getPersonalQrData(@Path("storeCode") String storeCode);

    /**
     * 提交发展新会员数据
     * @return
     */
    @POST("front/member/expand/")
    Observable<DevMemberRespone> getDevMemberData(@Body DevMemberRequest devMemberRequest);

    /**
     * 提交会员备注
     * @return
     */
    @POST("front/member/setWxUserRemark/")
    Observable<ResponeBean> submitRemarks(@Body RemarksRequest remarksRequest);

    /**
     * 在会员详情中获取会员参加预约活动的列表
     * @param conditionRequest
     * @return
     */
    @POST("front/prom/member/enroll/")
    Observable<ReservationServerBean> getReservationServerData(@Body ConditionRequest conditionRequest);



    /**
     * 根据门店获取待办事项
     * @param storeCode
     * @return
     */
    @GET("front/prom/store/{storeCode}")
    Observable<StoreScheduleBean> getScheduleData(@Path("storeCode") String storeCode);

    /**
     * 根据活动查询参与活动的人【爱会员】
     * @param infoId
     * @param storeId
     * @return
     */
    @GET("front/prom/getEnroll/{infoId}/{storeId}")
    Observable<ActionMembersBean> getMembersbyAction(@Path("infoId") long infoId, @Path("storeId") long storeId);

    /**
     * 参与活动人详情【爱会员】
     * @param id
     * @return
     */
    @GET("front/prom/enrollInfo/{id}")
    Observable<ActionMembersBean> getActionMemberDetial(@Path("id") long id);

    /**
     * 获取会员数据
     * @param storeId
     * @return
     */
    @GET("front/member/getStatistics/")
    Observable<MemberReportBean> getMemberReportData(@Query("storeId") String storeId);

    /**
     * 获取今日新增
     * @param storeId
     * @return
     */
    @GET("front/member/getStatisticsMemberDaily/")
    Observable<MemberAddBean> getTodayNewAddData(@Query("storeId") String storeId);


    @POST("front/member/getByTags/")
    Observable<PurFollowDetialBean> getPurFollowMemberData(@Query("page") int page, @Query("size") int size, @Body ConditionRequest conditionRequest);

}
