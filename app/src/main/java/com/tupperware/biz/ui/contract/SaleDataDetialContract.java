package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.saleenter.SaleReportBean;

/**
 * Created by dhunter on 2018/6/11.
 */

public class SaleDataDetialContract {
    public interface View {
        void setSaleReportData(SaleReportBean saleReportBean);
        void hideDialog();
        void toast(String msg);
        void reLogin();
    }

    public interface Presenter {
        void getSaleReportData(String storeCode, int type);
    }
}
