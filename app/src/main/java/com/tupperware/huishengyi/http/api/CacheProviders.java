package com.tupperware.huishengyi.http.api;

import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.entity.college.CollegeTabBean;
import com.tupperware.huishengyi.entity.college.CourseBean;
import com.tupperware.huishengyi.entity.home.HomeBean;
import com.tupperware.huishengyi.entity.member.ActionMembersBean;
import com.tupperware.huishengyi.entity.member.GiftBean;
import com.tupperware.huishengyi.entity.member.MemberAddBean;
import com.tupperware.huishengyi.entity.member.MemberReportBean;
import com.tupperware.huishengyi.entity.member.PersonalQrBean;
import com.tupperware.huishengyi.entity.member.ReservationServerBean;
import com.tupperware.huishengyi.entity.member.StoreScheduleBean;
import com.tupperware.huishengyi.entity.msg.MsgBean;
import com.tupperware.huishengyi.entity.msg.MsgItemBean;
import com.tupperware.huishengyi.entity.order.OrderBean;
import com.tupperware.huishengyi.entity.order.OrderItemBean;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;
import com.tupperware.huishengyi.entity.saleenter.SaleReportBean;
import com.tupperware.huishengyi.entity.saleenter.SaleTabBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;


/**
 * Created by dhunter on 2018/4/20.
 */

public interface CacheProviders {

    //缓存时间 1天
    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<HomeBean>>> getHomePageTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    //缓存时间 2天
    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<CollegeTabBean>>> getCollegeTabTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    //缓存时间 2天
    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<CollegeBean>>> getArticleTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    //缓存时间 2天
    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<CollegeBean>>> getExperienceTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    //缓存时间 2天
    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<CollegeBean>>> getCourseTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<CollegeBean>>> getAdvertTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<CourseBean>>> getCourDetialTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);
//EvictProvider表示是否强制刷新,下拉刷新时就需要强制刷新，DynamicKey表示要缓存第几页数据，当不传此参数时默认缓存第一页
//    Observable<TestEntity> getApkVersson2(Observable<TestEntity> obs, EvictProvider evictProvider, DynamicKey dynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<OrderBean>>> getOrderTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<OrderItemBean>>> getOrderItemTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<MsgBean>>> getMsgTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);
    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<MsgItemBean>>> getMsgItemTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<SaleTabBean>>> geSaleTabTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<SaleEnterBean>>> getSaleEnterTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<SaleEnterBean>>> getSaleHistoryTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<SaleEnterBean>>> getSaleScanTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<PersonalQrBean>>> getPersonalQrTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<ReservationServerBean>>> getReservationServerType(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<GiftBean>>> getGiftListType(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<StoreScheduleBean>>> getScheduleType(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<SaleReportBean>>> getSaleReportTypes(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);


    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<MemberReportBean>>> getMemberReportType(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<MemberAddBean>>> getTodayNewAddType(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<ActionMembersBean>>> getMembersbyActionType(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    Observable<Reply<List<ActionMembersBean>>> getActionMemberDetialType(Observable observable, DynamicKey userName, EvictDynamicKey evictDynamicKey);

}
