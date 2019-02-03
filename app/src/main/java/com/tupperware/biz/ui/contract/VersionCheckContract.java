package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.home.VersionUpBean;

/**
 * Created by dhunter on 2018/4/25.
 */

public class VersionCheckContract {
    public interface View {
//        void getVersion();
        void toast(String string);
        void showUpdateChooseDialog(VersionUpBean verinfo);
        void showMustUpdateChooseDialog(VersionUpBean verinfo);
        void reLogin();
    }

    public interface Presenter {
        void checkVersion(String mUserId);
    }
}
