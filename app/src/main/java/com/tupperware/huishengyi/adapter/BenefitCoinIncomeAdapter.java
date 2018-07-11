package com.tupperware.huishengyi.adapter;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;
import com.tupperware.huishengyi.utils.SystemUtils;

/**
 * Created by dhunter on 2018/3/21.
 */

public class BenefitCoinIncomeAdapter extends BaseQuickAdapter<BenefitCoinExpenditureBean.ExpenditureContentBean,BaseViewHolder> {

    public BenefitCoinIncomeAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, BenefitCoinExpenditureBean.ExpenditureContentBean item, int position) {
//        helper.setText(R.id.goods_name, item.goodsName);
//        helper.setText(R.id.date, item.date);
//        helper.setText(R.id.user_name, item.userName);
//        helper.setText(R.id.income_money, item.money);
        helper.setText(R.id.date, SystemUtils.getCurrentDate());
        helper.setText(R.id.user_name, item.userName);
        helper.setText(R.id.income_money, item.money);
    }
}
