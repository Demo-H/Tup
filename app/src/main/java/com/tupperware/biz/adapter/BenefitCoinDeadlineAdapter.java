package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.benefit.CouponResponse;
import com.tupperware.biz.ui.activities.MemberBenefitDetialActivity;

/**
 * Created by dhunter on 2018/4/2.
 */

public class BenefitCoinDeadlineAdapter extends BaseQuickAdapter<CouponResponse.ExpenditureContentBean,BaseViewHolder> {

    private String mSelectTitle;

    public BenefitCoinDeadlineAdapter(int layoutResId, String mSelectTitle) {
        super(layoutResId);
        this.mSelectTitle = mSelectTitle;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponResponse.ExpenditureContentBean item, int position) {
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
