package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;

/**
 * Created by dhunter on 2018/4/2.
 */

public class BenefitCoinDeadlineContract {
    public interface View {
        void setBenefitCoinDeadlineData(BenefitCoinExpenditureBean benefitCoinExpenditure);
    }

    public interface Presenter {
        void getBenefitCoinDeadlineData();
    }
}
