package com.tupperware.biz.ui.contract;

/**
 * Created by dhunter on 2018/7/10.
 */

public class RegisterChooseContract {
    public interface View {
        void toast(String msg);
        void hideDialog();
        void setUseCouponResult(String msg);
        void reLogin();
    }

    public interface Presenter {
        void useProductCoupon(String qrCode, String uniqueCode, Integer memberId, int isUpgrade);
    }
}
