package com.tupperware.huishengyi.http;

import com.android.dhunter.common.network.DataManager;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.NetWorkUrl;
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
import com.tupperware.huishengyi.http.api.PersonalService;
import com.tupperware.huishengyi.utils.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

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
        Observable observable = getService(PersonalService.class).logout(NetWorkUrl.LOGOUT_URL);
        return changeIOToMainThread(observable, consumer);
    }

    /** 修改密码 **/
    public Disposable modifiedPwd(DisposableObserver<ResponseBean> consumer, ModifiedPwdRequest request) {
        Observable observable = getService(PersonalService.class).modifiedPwd(NetWorkUrl.MODFIED_PASSWORD, request);
        return changeIOToMainThread(observable, consumer);
    }


    /**
     * 获取我的微店
     * GET
     */
    public Disposable getPersonalQrData(DisposableObserver<PersonalQrBean> consumer, String storeCode) {
        Observable observable = getService(PersonalService.class).getPersonalQrData(storeCode);
        Observable observableCahce = providers.getPersonalQrTypes(observable, new DynamicKey("personalqr"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<PersonalQrBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 提交发展新会员数据
     */
    public Disposable getDevMemberData(DisposableObserver<DevMemberRespone> consumer, DevMemberRequest devMemberRequest) {
        Observable observable = getService(PersonalService.class).getDevMemberData(devMemberRequest);
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
    public Disposable getMemberReportData(DisposableObserver<MemberReportBean> consumer, String storeId) {
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
    public Disposable getTodayNewAddData(DisposableObserver<MemberAddBean> consumer, String storeId) {
        Observable observable = getService(PersonalService.class).getTodayNewAddData(storeId);
        Observable observableCahce = providers.getTodayNewAddType(observable, new DynamicKey("new_add"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<MemberAddBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }


    public Disposable getPurFollowMemberData(DisposableObserver<PurFollowDetialBean> consumer, String tagCodes, int pageIndex) {
        Observable observable = getService(PersonalService.class).getPurFollowMemberData(pageIndex, Constant.DEFAULT_MEMBER_PAGE_SIZE, getTagRequest(tagCodes));
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

    private ConditionRequest getTagRequest(String tagCodes) {
        ConditionRequest request = new ConditionRequest();
        List<String> stringlist = new ArrayList<>();
        stringlist.add(tagCodes);
        request.setTagCodes(stringlist);
        String json = ObjectUtil.jsonFormatter(request);
        return request;
    }



//    private GiftRequest getGiftRequest(String member_id, int pageIndex) {
//        GiftRequest request = new GiftRequest();
//        request.setMemberId(member_id);
//        request.setPageNo(pageIndex);
//        request.setPageSize(Constant.DEFAULT_MEMBER_PAGE_SIZE);
//        return request;
//    }

}
