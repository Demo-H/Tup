package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;

/**
 * Created by dhunter on 2018/5/28.
 */

public class SaleEnterClassifyContract {
    public interface View {
        void setSaleEnterData(SaleEnterBean mBean);
//        void setMoreSaleEnterData(SaleEnterBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
    }

    public interface Presenter {
        void getSaleEnterData(String storeCode, String date, String seriesId);
//        void getMoreSaleEnterData();
    }
}
