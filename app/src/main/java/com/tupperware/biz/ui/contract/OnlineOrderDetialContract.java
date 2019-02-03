package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.order.OrderItemBean;

/**
 * Created by dhunter on 2018/5/17.
 */

public class OnlineOrderDetialContract {
    public interface View {
        void setOrderItemData(OrderItemBean orderItemBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void reLogin();
    }

    public interface Presenter {
        void getOrderItemData(long id);
    }
}
