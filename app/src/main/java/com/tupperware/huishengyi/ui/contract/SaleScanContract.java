package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;

/**
 * Created by dhunter on 2018/6/4.
 */

public class SaleScanContract {
    public interface View {
        void setSaleScanData(SaleEnterBean mBean);
        void setError();
        void toast(String msg);
    }

    public interface Presenter {
        void getSaleScanData(String storeCode, String date, String barCode);
    }
}
