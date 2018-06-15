package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.StaffManagerBean;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerContract {
    public interface View {
        void setStaffManagerData(StaffManagerBean staffManagerBean);
    }

    public interface Presenter {
        void getStaffManagerData();
    }
}
