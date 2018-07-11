package com.tupperware.huishengyi.http;

import android.support.v4.util.ArrayMap;

import com.android.dhunter.common.network.DataManager;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.NetWorkUrl;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.entity.member.GiftBean;
import com.tupperware.huishengyi.entity.member.MemberBean;
import com.tupperware.huishengyi.entity.saleenter.MemUpgradeRequest;
import com.tupperware.huishengyi.http.api.MemberService;

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

    public Disposable getMemberSearchData(DisposableObserver<MemberBean> consumer, String memberCode, int pageIndex) {
        Observable observable = getService(MemberService.class).getMemberSearchData(NetWorkUrl.MEMBER_SEARCH_LIST, getMemberSearchRequest(memberCode, pageIndex));
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable getMemberDetialData(DisposableObserver<MemberBean> consumer, String mobileNum) {
        Observable observable = getService(MemberService.class).getMemberDetialData(NetWorkUrl.MEMBER_DETIAL, mobileNum);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 获取礼物寄送列表
     * @param pageIndex
     * @return
     */
    public Disposable getGiftListData(DisposableObserver<GiftBean> consumer, String member_id, int pageIndex) {
        Observable observable = getService(MemberService.class).getGiftListData(NetWorkUrl.MEMBER_GIFT_LIST, getMemberGiftListRequest(member_id, pageIndex));
        Observable observableCahce = providers.getGiftListType(observable, new DynamicKey("gift_list"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<GiftBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /** 券码核销--会员升级 **/
    public Disposable getSMSCode(DisposableObserver<ResponseBean> consumer, String phoneNum) {
        Observable observable = getService(MemberService.class).getSMSCode(NetWorkUrl.GET_VALIDATE_CODE, phoneNum);
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable startRegister(DisposableObserver<ResponseBean> consumer, MemUpgradeRequest request) {
        Observable observable = getService(MemberService.class).startRegister(NetWorkUrl.MEMBER_UPGRADE, request);
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

    private Map<String, Object> getMemberGiftListRequest(String memberId, int pageIndex) {
        Map<String, Object> maps = new ArrayMap<>();
        maps.put("memberId", memberId);
        maps.put("pageNo", pageIndex);
        maps.put("pageSize", Constant.DEFAULT_MEMBER_PAGE_SIZE);
        return maps;
    }
}
