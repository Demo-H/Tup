package com.tupperware.biz.ui.contract;


import com.tupperware.biz.entity.benefit.BenefitCoinResponse;
import com.tupperware.biz.entity.benefit.CouponResponse;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinExpenditureContract {
    public interface View {
        void setBenefitCoinExpenditureData(BenefitCoinResponse mBean);
        void setMoreBenefitCoinExpenditureData(BenefitCoinResponse mBean);
        void setCouponListData(CouponResponse mBean);
        void setMoreCouponListData(CouponResponse mBean);
        void hideDialog();
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void reLogin();
    }

    public interface Presenter {
        void getBenefitCoinExpenditureData(int status);
        void getMoreBenefitCoinExpenditureData(int status, int pageIndex);
        void getCouponListData(int statue);
        void getMoreCouponListData(int statue, int pageIndex);
    }
}

