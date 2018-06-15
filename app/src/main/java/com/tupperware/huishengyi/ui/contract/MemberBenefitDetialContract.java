package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;

/**
 * Created by dhunter on 2018/4/2.
 */

public class MemberBenefitDetialContract {

    public interface View {
        void setMemberBenefitDetialData(BenefitCoinExpenditureBean benefitCoinExpenditure);
    }

    public interface Presenter {
        void getMemberBenefitDetialData();
    }
}
