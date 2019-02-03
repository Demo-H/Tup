package com.tupperware.biz.ui.contract;

/**
 * Created by dhunter on 2018/7/5.
 */

public class PersonalSettingContract {

    public interface View {
        void setLogoutSuccess();
        void toast(String msg);
        void hideDialog();
    }

    public interface Presenter {
        void logout();
    }
}
