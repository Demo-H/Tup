package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.login.LoginInfo;

/**
 * Created by dhunter on 2018/7/4.
 */

public class LoginContract {
    public interface View {
        void showLoginResult(LoginInfo loginInfo);
        void hideDialog();
        void toast(String msg);
    }

    public interface Presenter {
        void trylogin(String userName, String psw);
    }
}
