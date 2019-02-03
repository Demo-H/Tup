package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.VerifyProduct;

/**
 * Created by dhunter on 2018/7/10.
 */

public class ProductVerifyContract {

    public interface View {
        void toast(String msg);
        void hideDialog();
        void setErrorShow();
        void setProductVerifyResult(VerifyProduct verifyProduct);
        void setUseCouponResult(String msg);
        void reLogin();
    }

    public interface Presenter {
        void verifyProductCode(String qrCode, String uniqueCode);
        void useProductCoupon(String qrCode, String uniqueCode, Integer memberId, int isUpgrade);
    }
}
