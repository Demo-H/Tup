package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.order.OrderBean;

/**
 * Created by dhunter on 2018/3/15.
 */

public class ResOrderPendingContract {
    public interface View {
        void setResOrderPendingData(OrderBean orderBean);
        void setMoreResOrderPendingData(OrderBean orderBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
    }

    public interface Presenter {
        void getResOrderPendingData(String code, String status);
        void getMoreResOrderPendingData(String code, String status, int pageIndex);
    }
}
