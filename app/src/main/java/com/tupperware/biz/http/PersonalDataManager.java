package com.tupperware.biz.http;


import android.support.v4.util.ArrayMap;

import com.android.dhunter.common.network.DataManager;
import com.tupperware.biz.config.Constant;
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
import com.tupperware.biz.http.api.PersonalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * Created by dhunter on 2018/6/4.
 */

public class PersonalDataManager extends BaseDataManager {

    public PersonalDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static PersonalDataManager getInstance(DataManager dataManager){
        return new PersonalDataManager(dataManager);
    }

    /** 退出登录 **/
    public Disposable logout(DisposableObserver<ResponseBean> consumer) {
        Observable observable = getService(PersonalService.class).logout();
        return changeIOToMainThread(observable, consumer);
    }

    /** 修改密码 **/
    public Disposable modifiedPwd(DisposableObserver<ResponseBean> consumer, ModifiedPwdRequest request) {
        Observable observable = getService(PersonalService.class).modifiedPwd(request);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 获取我的页面惠金币和券码数
     */
    public Disposable getBenifitCoinNum(DisposableObserver<MeBenfitCoin> consumer, Integer storeId) {
        Observable observable = getService(PersonalService.class).getBenifitCoinNum(storeId);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 获取惠金币明细列表
     */
    public Disposable getBenifitCoinList(DisposableObserver<BenefitCoinResponse> consumer, int statue, int pageIndex) {
        Observable observable = getService(PersonalService.class).getBenifitCoinList(getBenefitListReqeust(statue, pageIndex));
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 获取优惠券明细列表
     */
    public Disposable getCouponList(DisposableObserver<CouponResponse> consumer, int statue, int pageIndex) {
        Observable observable = getService(PersonalService.class).getCouponList(getCouponListReqeust(statue, pageIndex));
        return changeIOToMainThread(observable, consumer);
    }


    /**
     * 获取我的微店
     * GET
     */
    public Disposable getPersonalQrData(DisposableObserver<PersonalQrBean> consumer, String storeCode) {
        Observable observable = getService(PersonalService.class).getPersonalQrData(storeCode);
//        Observable observableCahce = providers.getPersonalQrTypes(observable, new DynamicKey("personalqr"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<PersonalQrBean>>());
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 提交发展新会员数据
     */
    public Disposable getDevMemberData(DisposableObserver<DevMemberRespone> consumer, DevMemberRequest devMemberRequest) {
        Observable observable = getService(PersonalService.class).getDevMemberData(devMemberRequest);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 发送微信公众号消息模板
     */
    public Disposable sendMessageToWechat(DisposableObserver<BaseResponse> consumer, SendMessageRequest requestData) {
        Observable observable = getService(PersonalService.class).sendMessageToWechat(requestData);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 提交备注
     */
    public Disposable submitRemarks(DisposableObserver<ResponeBean> consumer, RemarksRequest remarksRequest) {
        Observable observable = getService(PersonalService.class).submitRemarks(remarksRequest);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 会员预约活动
     */
    public Disposable getReservationServerData(DisposableObserver<ReservationServerBean> consumer, String mobile, String storeCode, int pageIndex) {
        Observable observable = getService(PersonalService.class).getReservationServerData(getRequest(mobile, storeCode, pageIndex));
        Observable observableCahce = providers.getReservationServerType(observable, new DynamicKey("order_list"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<ReservationServerBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 根据门店获取待办事项
     */
    public Disposable getScheduleData(DisposableObserver<StoreScheduleBean> consumer, String storeCode) {
        Observable observable = getService(PersonalService.class).getScheduleData(storeCode);
        Observable observableCahce = providers.getScheduleType(observable, new DynamicKey("schedule_list"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<StoreScheduleBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 根据活动查询参与活动的人【爱会员】
     * @param consumer
     * @param infoId
     * @param storeId
     * @return
     */
    public Disposable getMembersbyAction(DisposableObserver<ActionMembersBean> consumer, long infoId, long storeId) {
        Observable observable = getService(PersonalService.class).getMembersbyAction(infoId, storeId);
        Observable observableCahce = providers.getMembersbyActionType(observable, new DynamicKey("action_members"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<ActionMembersBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 参与活动人详情【爱会员】
     * @param consumer
     * @param id
     * @return
     */
    public Disposable getActionMemberDetial(DisposableObserver<ActionMembersBean> consumer, long id) {
        Observable observable = getService(PersonalService.class).getActionMemberDetial(id);
        Observable observableCahce = providers.getActionMemberDetialType(observable, new DynamicKey("member_action_detial"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<ActionMembersBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 获取会员数据
     */
    public Disposable getMemberReportData(DisposableObserver<MemberReportBean> consumer, Integer storeId) {
        Observable observable = getService(PersonalService.class).getMemberReportData(storeId);
        Observable observableCahce = providers.getMemberReportType(observable, new DynamicKey("member_report"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<MemberReportBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 获取今日新增
     * @param consumer
     * @param storeId
     * @return
     */
    public Disposable getTodayNewAddData(DisposableObserver<MemberAddBean> consumer, Integer storeId) {
        Observable observable = getService(PersonalService.class).getTodayNewAddData(storeId);
        Observable observableCahce = providers.getTodayNewAddType(observable, new DynamicKey("new_add"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<MemberAddBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 店员管理接口
     * @param consumer
     * @param storeId
     * @return
     */
    public Disposable getStaffListData(DisposableObserver<StaffManagerBean> consumer, Integer storeId) {
        Observable observable = getService(PersonalService.class).getStaffListData(storeId);
        Observable observableCahce = providers.getStaffListDataType(observable, new DynamicKey("staff_list"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<StaffManagerBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    public Disposable addStaffData(DisposableObserver<BaseResponse> consumer, StaffRequest reqData) {
        Observable observable = getService(PersonalService.class).addStaffData(reqData);
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable updateStaffData(DisposableObserver<BaseResponse> consumer, StaffRequest reqData) {
        Observable observable = getService(PersonalService.class).updateStaffData(reqData);
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable getPurFollowMemberData(DisposableObserver<PurFollowDetialBean> consumer, String tagCodes, Integer currentStoreId, int pageIndex) {
        Observable observable = getService(PersonalService.class).getPurFollowMemberData(pageIndex, Constant.DEFAULT_MEMBER_PAGE_SIZE, getTagRequest(tagCodes, currentStoreId));
        Observable observableCahce = providers.getPurFollowMemberType(observable, new DynamicKey("pur_member"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<PurFollowDetialBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }


    private ConditionRequest getRequest(String mobile, String storeCode, int pageIndex) {
        ConditionRequest request = new ConditionRequest();
        ConditionRequest.Condition condition = new ConditionRequest.Condition();
        condition.setMobile(mobile);
        condition.setStoreCode(storeCode);
        request.setCondition(condition);
        ConditionRequest.PageQuery query = new ConditionRequest.PageQuery();
        query.setPageIndex(pageIndex);
        query.setPageSize(Constant.DEFAULT_MEMBER_PAGE_SIZE);
//        query.setTotalRows(0);
        request.setPagingQuery(query);
        return request;
    }

    private ConditionRequest getTagRequest(String tagCodes, Integer currentStoreId) {
        ConditionRequest request = new ConditionRequest();
        List<String> stringlist = new ArrayList<>();
        stringlist.add(tagCodes);
        request.setTagCodes(stringlist);
        request.setCurrentStoreId(currentStoreId);
        return request;
    }

    private Map<String, Object> getCouponListReqeust(int statue, int pageIndex) {
        Map<String, Object> maps = new ArrayMap<>();
        maps.put("statue", statue + "");
        maps.put("page", pageIndex);
        maps.put("size", Constant.DEFAULT_MEMBER_PAGE_SIZE);
        return maps;
    }

    private Map<String, Object> getBenefitListReqeust(int statue, int pageIndex) {
        Map<String, Object> maps = new ArrayMap<>();
        maps.put("status", statue);
        maps.put("requestDate", System.currentTimeMillis() / 1000);
        maps.put("pageNo", pageIndex);
        maps.put("pageSize", Constant.DEFAULT_MEMBER_PAGE_SIZE);
        return maps;
    }



//    private GiftRequest getGiftRequest(String member_id, int pageIndex) {
//        GiftRequest request = new GiftRequest();
//        request.setMemberId(member_id);
//        request.setPageNo(pageIndex);
//        request.setPageSize(Constant.DEFAULT_MEMBER_PAGE_SIZE);
//        return request;
//    }

}
