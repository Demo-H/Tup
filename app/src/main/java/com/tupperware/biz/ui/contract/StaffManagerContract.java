package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.StaffManagerBean;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerContract {
    public interface View {
        void setStaffManagerData(StaffManagerBean staffManagerBean);
        void toast(String msg);
        void hideDialog();
        void reLogin();
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
    }

    public interface Presenter {
        void getStaffManagerData(Integer storeId);
    }
}
