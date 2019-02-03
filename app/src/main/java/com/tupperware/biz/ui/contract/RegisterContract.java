package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.saleenter.MemUpgradeRequest;

/**
 * Created by dhunter on 2018/7/10.
 */

public class RegisterContract {

    public interface View {
        void toast(String msg);
        void hideDialog();
        void setSmsCodeResult();
        void setSMSCodeError();
        void setRegisterResult();
        void setRegisterError();
        void reLogin();
    }

    public interface Presenter {
        void getSMSCode(String phone);
        void startRegister(MemUpgradeRequest request);
    }
}
