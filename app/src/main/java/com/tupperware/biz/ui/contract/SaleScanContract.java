package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.saleenter.SaleEnterBean;

/**
 * Created by dhunter on 2018/6/4.
 */

public class SaleScanContract {
    public interface View {
        void setSaleScanData(SaleEnterBean mBean);
        void setError();
        void toast(String msg);
        void reLogin();
    }

    public interface Presenter {
        void getSaleScanData(String storeCode, String date, String barCode);
    }
}
