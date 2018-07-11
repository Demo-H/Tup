package com.tupperware.huishengyi.http;

import com.android.dhunter.common.network.DataManager;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.ConditionRequest;
import com.tupperware.huishengyi.entity.order.OrderBean;
import com.tupperware.huishengyi.entity.order.OrderItemBean;
import com.tupperware.huishengyi.http.api.OrderService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * Created by dhunter on 2018/5/16.
 */

public class OrderDataManager extends BaseDataManager {

    public OrderDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static OrderDataManager getInstance(DataManager dataManager){
        return new OrderDataManager(dataManager);
    }

    public Disposable getOrderData(DisposableObserver<OrderBean> consumer, String code, String status) {
        Observable observable = getService(OrderService.class).getOrderData(getRequest(code, status, Constant.FIRST_PAGE_INDEX));
        Observable observableCahce = providers.getOrderTypes(observable, new DynamicKey("order_list"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<OrderBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    public Disposable getMoreOrderData(DisposableObserver<OrderBean> consumer, String code, String status, int pageIndex) {
        Observable observable = getService(OrderService.class).getOrderData(getRequest(code, status, pageIndex));
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable getOrderItemData(DisposableObserver<OrderItemBean> consumer, long id) {
        Observable observable = getService(OrderService.class).getOrderItemData(id);
        Observable observableCahce = providers.getOrderItemTypes(observable, new DynamicKey("order_item"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<OrderItemBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 云订单搜索
     * @param consumer
     * @param code
     * @param orderCode
     * @return
     */
    public Disposable getOrderSearchData(DisposableObserver<OrderBean> consumer, String code, String orderCode) {
        Observable observable = getService(OrderService.class).getOrderData(getSearchRequest(code, orderCode));
        return changeIOToMainThread(observable, consumer);
    }


    private ConditionRequest getRequest(String code, String status, int pageIndex) {
        ConditionRequest request = new ConditionRequest();
        ConditionRequest.Condition condition = new ConditionRequest.Condition();
        condition.setCode(code);
        condition.setStatus(status);
        request.setCondition(condition);
        ConditionRequest.PageQuery query = new ConditionRequest.PageQuery();
        query.setPageIndex(pageIndex);
        query.setPageSize(Constant.DEFAULT_PAGE_SIZE);
//        query.setTotalRows(0);
        request.setPagingQuery(query);
        return request;
    }

    private ConditionRequest getSearchRequest(String code, String orderCode) {
        ConditionRequest request = new ConditionRequest();
        ConditionRequest.Condition condition = new ConditionRequest.Condition();
        condition.setCode(code);
        condition.setOrderCode(orderCode);
        request.setCondition(condition);
        return request;
    }
}
