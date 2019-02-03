package com.tupperware.biz.adapter;

import android.content.Context;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.benefit.BenefitCoinInfo;
import com.tupperware.biz.utils.DateFormatter;

/**
 * Created by dhunter on 2018/9/6.
 */

public class BenefitCoinAllAdapter extends BaseQuickAdapter<BenefitCoinInfo,BaseViewHolder> {

    private Context mContext;

    public BenefitCoinAllAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BenefitCoinInfo item, int position) {
        helper.setText(R.id.goods_name, item.getProductName());
        helper.setText(R.id.date, DateFormatter.timesecondToDate(item.getUsedTime()));
        helper.setText(R.id.user_name, item.getMemberName());
        TextView textView = helper.getView(R.id.integral_money);
        //1是收入，0是支出
        if(item.getIsBilling() == 1) {
            textView.setTextColor(mContext.getResources().getColor(R.color.color_009944));
            textView.setText("+" + item.getIntegralAmount());
        } else if(item.getIsBilling() == 0) {
            textView.setTextColor(mContext.getResources().getColor(R.color.color_ff3b3b));
            textView.setText("-" + item.getIntegralAmount());
        }
    }
}