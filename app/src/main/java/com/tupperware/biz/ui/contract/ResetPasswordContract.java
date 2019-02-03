package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.login.ModifiedPwdRequest;

/**
 * Created by dhunter on 2018/7/6.
 */

public class ResetPasswordContract {
    public interface View {
        void setModifiedPwdSuccess();
        void toast(String msg);
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void modifiedPwd(ModifiedPwdRequest request);
    }
}
