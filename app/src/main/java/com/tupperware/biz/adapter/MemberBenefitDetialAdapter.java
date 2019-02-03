package com.tupperware.biz.adapter;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.benefit.CouponResponse;

/**
 * Created by dhunter on 2018/4/2.
 */

public class MemberBenefitDetialAdapter extends BaseQuickAdapter<CouponResponse.ExpenditureContentBean,BaseViewHolder> {

    public MemberBenefitDetialAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponResponse.ExpenditureContentBean item, int position) {
        //测试数据
        if(position == 1) {
            helper.setText(R.id.action_detial, "购买商品");
            helper.setText(R.id.expenditure_money, "+200");
            helper.setTextColor(R.id.expenditure_money, 0xff009944); // 设置R.color.color_009944
        }

    }
}
