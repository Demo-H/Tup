package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.benefit.CouponResponse;

/**
 * Created by dhunter on 2018/4/2.
 */

public class MemberBenefitDetialContract {

    public interface View {
        void setMemberBenefitDetialData(CouponResponse benefitCoinExpenditure);
    }

    public interface Presenter {
        void getMemberBenefitDetialData();
    }
}
