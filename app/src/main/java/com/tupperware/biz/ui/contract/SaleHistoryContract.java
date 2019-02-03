package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.saleenter.SaleEnterBean;

/**
 * Created by dhunter on 2018/5/28.
 */

public class SaleHistoryContract {
    public interface View {
        void setSaleHistoryData(SaleEnterBean mBean);
//        void setMoreSaleHistoryData(SaleEnterBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getSaleHistoryData(String storeCode, String date);
//        void getMoreSaleHistoryData();
    }
}
