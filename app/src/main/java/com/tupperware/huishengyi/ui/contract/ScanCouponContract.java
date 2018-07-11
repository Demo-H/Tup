package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.VerifyCoupon;
import com.tupperware.huishengyi.entity.VerifyProduct;

/**
 * Created by dhunter on 2018/7/10.
 */

public class ScanCouponContract {
    public interface View {
        void toast(String msg);
        void hideDialog();
        void setErrorShow(String msg);
        void setCouponCheckResult(VerifyCoupon verifyCoupon);
        void setProductVerifyResult(VerifyProduct verifyProduct);
    }

    public interface Presenter {
        void checkCoupon(String qrCode);
        void verifyProductCode(String qrCode, String uniqueCode);
    }

}
