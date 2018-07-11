package com.tupperware.huishengyi.ui.contract;

/**
 * Created by dhunter on 2018/7/10.
 */

public class RegisterChooseContract {
    public interface View {
        void toast(String msg);
        void hideDialog();
        void setUseCouponResult();
    }

    public interface Presenter {
        void useProductCoupon(String qrCode, String uniqueCode);
    }
}
