package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.saleenter.SaleTabBean;

/**
 * Created by dhunter on 2018/5/25.
 */

public class SaleHandContract {

    public interface View {
        void setLableData(SaleTabBean mBean);
        void setNetErrorView();
        void setNormalView();
        void toast(String msg);
        void reLogin();
//        void setTestData();
    }

    public interface Presenter {
        void getLableData();
    }
}
