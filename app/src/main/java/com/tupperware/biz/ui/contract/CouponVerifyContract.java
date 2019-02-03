package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.VerifyCoupon;

/**
 * Created by dhunter on 2018/7/10.
 */

public class CouponVerifyContract {

    public interface View {
        void toast(String msg);
        void hideDialog();
        void setErrorShow();
        void setCouponCheckResult(VerifyCoupon verifyCoupon);
        void reLogin();
    }

    public interface Presenter {
        void checkCoupon(String qrCode);
    }
}
