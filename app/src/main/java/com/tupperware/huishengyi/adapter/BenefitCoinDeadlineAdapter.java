package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;
import com.tupperware.huishengyi.ui.MemberBenefitDetialActivity;

/**
 * Created by dhunter on 2018/4/2.
 */

public class BenefitCoinDeadlineAdapter extends BaseQuickAdapter<BenefitCoinExpenditureBean.ExpenditureContentBean,BaseViewHolder> {

    private String mSelectTitle;

    public BenefitCoinDeadlineAdapter(int layoutResId, String mSelectTitle) {
        super(layoutResId);
        this.mSelectTitle = mSelectTitle;
    }

    @Override
    protected void convert(BaseViewHolder helper, BenefitCoinExpenditureBean.ExpenditureContentBean item, int position) {
        if(mSelectTitle.equals(Constant.COUPON)) {
            helper.getView(R.id.coupon_benefit_icon).setBackgroundResource(R.mipmap.meb_gift_list_ic);
        } else if(mSelectTitle.equals(Constant.BENEFIT)) {
            helper.getView(R.id.coupon_benefit_icon).setBackgroundResource(R.mipmap.meb_con_list_ic);
        }
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), MemberBenefitDetialActivity.class);
                intent.putExtra(Constant.ACTIVITY_TITLE, mSelectTitle);
                view.getContext().startActivity(intent);
            }
        });

    }
}
