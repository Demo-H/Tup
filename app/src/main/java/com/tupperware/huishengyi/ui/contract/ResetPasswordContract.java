package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.login.ModifiedPwdRequest;

/**
 * Created by dhunter on 2018/7/6.
 */

public class ResetPasswordContract {
    public interface View {
        void setModifiedPwdSuccess();
        void toast(String msg);
        void hideDialog();
    }

    public interface Presenter {
        void modifiedPwd(ModifiedPwdRequest request);
    }
}
