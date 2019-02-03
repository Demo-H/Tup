package com.tupperware.biz.http.api;

import com.tupperware.biz.entity.college.ConditionRequest;
import com.tupperware.biz.entity.order.OrderBean;
import com.tupperware.biz.entity.order.OrderItemBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dhunter on 2018/5/16.
 */

public interface OrderService {

    /**
     * 获取云订单
     * @param conditionRequest
     * @return
     */
    @POST("front/pos/order/list/")
    Observable<OrderBean> getOrderData(@Body ConditionRequest conditionRequest);

    /**
     * 获取云订单详情
     * @param id
     * @return
     */
    @GET("front/pos/order/get/{id} ")
    Observable<OrderItemBean> getOrderItemData(@Path("id") long id);
}
