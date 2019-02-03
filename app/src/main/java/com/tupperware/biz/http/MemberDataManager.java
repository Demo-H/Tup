package com.tupperware.biz.http;

import android.support.v4.util.ArrayMap;

import com.android.dhunter.common.network.DataManager;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.login.ResponseBean;
import com.tupperware.biz.entity.member.GiftBean;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;
import com.tupperware.biz.entity.member.RemindMemberResponse;
import com.tupperware.biz.entity.product.ProductType;
import com.tupperware.biz.entity.saleenter.MemUpgradeRequest;
import com.tupperware.biz.http.api.MemberService;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * Created by dhunter on 2018/7/6.
 */

public class MemberDataManager extends BaseDataManager {

    public MemberDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static MemberDataManager getInstance(DataManager dataManager){
        return new MemberDataManager(dataManager);
    }

    /**
     * 获取会员列表
     * @param consumer
     * @param searchCondition， 搜索条件
     * @return
     */
    public Disposable getMemberList(DisposableObserver<MemberBean> consumer, MemberSearchConditionDTO searchCondition) {
        Observable observable = getService(MemberService.class).getMemberList(searchCondition);
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable getMemberFilterProductType(DisposableObserver<ProductType> consumer) {
        Observable observable = getService(MemberService.class).getMemberFilterProductType();
        return changeIOToMainThread(observable, consumer);
    }

//    public Disposable getMemberSearchData(DisposableObserver<MemberBean> consumer, String memberCode, int pageIndex) {
//        Observable observable = getService(MemberService.class).getMemberSearchData(NetWorkUrl.MEMBER_SEARCH_LIST, getMemberSearchRequest(memberCode, pageIndex));
//        return changeIOToMainThread(observable, consumer);
//    }

    public Disposable getMemberDetialData(DisposableObserver<MemberBean> consumer, Integer memberId, String mobileNum, int storeId, String employeeCode) {
        Observable observable = getService(MemberService.class).getMemberDetialData(memberId, mobileNum, storeId, employeeCode);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 获取礼物寄送列表
     * @param pageIndex
     * @return
     */
    public Disposable getGiftListData(DisposableObserver<GiftBean> consumer, Integer member_id, int pageIndex) {
        Observable observable = getService(MemberService.class).getGiftListData(getMemberGiftListRequest(member_id, pageIndex));
        Observable observableCahce = providers.getGiftListType(observable, new DynamicKey("gift_list"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<GiftBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /** 券码核销--会员升级 **/
    public Disposable getSMSCode(DisposableObserver<ResponseBean> consumer, String phoneNum) {
        Observable observable = getService(MemberService.class).getSMSCode(phoneNum);
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable startRegister(DisposableObserver<ResponseBean> consumer, MemUpgradeRequest request) {
        Observable observable = getService(MemberService.class).startRegister(request);
        return changeIOToMainThread(observable, consumer);
    }

    /** 净水器换滤芯提醒列表 **/
    public Disposable getFilterReservationList(DisposableObserver<RemindMemberResponse> consumer, Map<String, Object> maps) {
        Observable observable = getService(MemberService.class).getFilterReservationList(maps);
        return changeIOToMainThread(observable, consumer);
    }

    private Map<String, Object> getMemberSearchRequest(String memberCode, int pageIndex) {
        Map<String, Object> maps = new ArrayMap<>();
        maps.put("searchTxt", memberCode);
        maps.put("initiation_end", System.currentTimeMillis());
        maps.put("pageNo", pageIndex);
        maps.put("pageSize", Constant.DEFAULT_MEMBER_PAGE_SIZE);
        return maps;
    }

    private Map<String, Object> getMemberGiftListRequest(Integer memberId, int pageIndex) {
        Map<String, Object> maps = new ArrayMap<>();
        maps.put("memberId", memberId);
        maps.put("pageNo", pageIndex);
        maps.put("pageSize", Constant.DEFAULT_MEMBER_PAGE_SIZE);
        return maps;
    }
}
