package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.StaffRequest;

/**
 * Created by dhunter on 2018/10/24.
 */

public class AddNewStaffContract {
    public interface View {
        void addStaffDatasuccess();
        void updateStaffDatasuccess();
        void toast(String msg);
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void addStaffData(StaffRequest reqData);
        void updateStaffData(StaffRequest reqData);
    }
}
