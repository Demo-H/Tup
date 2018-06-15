package com.tupperware.huishengyi.adapter;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;

/**
 * Created by dhunter on 2018/4/2.
 */

public class MemberBenefitDetialAdapter extends BaseQuickAdapter<BenefitCoinExpenditureBean.ExpenditureContentBean,BaseViewHolder> {

    public MemberBenefitDetialAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BenefitCoinExpenditureBean.ExpenditureContentBean item, int position) {
        //测试数据
        if(position == 1) {
            helper.setText(R.id.action_detial, "购买商品");
            helper.setText(R.id.expenditure_money, "+200");
            helper.setTextColor(R.id.expenditure_money, 0xff009944); // 设置R.color.color_009944
        }

    }
}
