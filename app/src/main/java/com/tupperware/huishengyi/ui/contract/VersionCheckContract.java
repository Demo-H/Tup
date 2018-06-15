package com.tupperware.huishengyi.ui.contract;

/**
 * Created by dhunter on 2018/4/25.
 */

public class VersionCheckContract {
    public interface View {
//        void getVersion();
        void toast(String string);
        void showUpdateChooseDialog(String title, String downloadurl);
    }

    public interface Presenter {
        void checkVersion(String mUserId);
    }
}
