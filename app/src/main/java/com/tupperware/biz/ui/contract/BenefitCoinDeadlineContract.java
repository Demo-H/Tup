package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.benefit.CouponResponse;

/**
 * Created by dhunter on 2018/4/2.
 */

public class BenefitCoinDeadlineContract {
    public interface View {
        void setBenefitCoinDeadlineData(CouponResponse benefitCoinExpenditure);
    }

    public interface Presenter {
        void getBenefitCoinDeadlineData();
    }
}
