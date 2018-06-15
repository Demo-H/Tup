package com.tupperware.huishengyi.ui.contract;


import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinExpenditureContract {
    public interface View {
        void setBenefitCoinExpenditureData(BenefitCoinExpenditureBean benefitCoinExpenditure);
//        void setMoreBenefitCoinExpenditureData(BenefitCoinExpenditureBean benefitCoinExpenditure);
    }

    public interface Presenter {
        void getBenefitCoinExpenditureData();
//        void getMoreBenefitCoinExpenditureData();
    }
}
