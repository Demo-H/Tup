package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;

/**
 * Created by dhunter on 2018/5/29.
 */

public class ProductSearchContract {
    public interface View {
        void setProductSearchData(SaleEnterBean mBean);
        void setMoreProductSearchData(SaleEnterBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
    }

    public interface Presenter {
        void getProductSearchData(String storeCode, String date, int type, String keyword, int pageIndex);
        void getMoreProductSearchData(String storeCode, String date, int type, String keyword, int pageIndex);
    }
}
