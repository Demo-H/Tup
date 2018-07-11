package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.login.ForgetPwInfo;
import com.tupperware.huishengyi.entity.login.ResetPwdRequest;

/**
 * Created by dhunter on 2018/7/6.
 */

public class ForgetPasswordContract {
    public interface View {
        void setPhoneData(ForgetPwInfo forgetPwInfo);
        void setSMSCodeSuccess();
        void setSMSCodeError();
        void setResetPwdSuccess();
        void hideDialog();
        void toast(String msg);
    }

    public interface Presenter {
        void getPhonebyStore(String storeEmployeeCode);
        void getSMSCode(String phoneNum);
        void forgetResetPwd(ResetPwdRequest request);
    }
}
