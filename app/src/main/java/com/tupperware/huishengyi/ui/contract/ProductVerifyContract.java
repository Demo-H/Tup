package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.VerifyProduct;

/**
 * Created by dhunter on 2018/7/10.
 */

public class ProductVerifyContract {

    public interface View {
        void toast(String msg);
        void hideDialog();
        void setErrorShow();
        void setProductVerifyResult(VerifyProduct verifyProduct);
        void setUseCouponResult();
    }

    public interface Presenter {
        void verifyProductCode(String qrCode, String uniqueCode);
        void useProductCoupon(String qrCode, String uniqueCode);
    }
}
