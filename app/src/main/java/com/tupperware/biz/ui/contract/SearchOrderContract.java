package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.order.OrderBean;

/**
 * Created by dhunter on 2018/5/30.
 */

public class SearchOrderContract {
    public interface View {
        void setOrderSearchData(OrderBean searchData);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getOrderSearchData(String code, String orderCode);
    }
}
