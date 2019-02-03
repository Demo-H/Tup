package com.tupperware.biz.adapter;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.entity.benefit.CouponResponse;

/**
 * Created by dhunter on 2018/4/2.
 */

public class MemberCouponDetialAdapter extends BaseQuickAdapter<CouponResponse.ExpenditureContentBean,BaseViewHolder> {

    public MemberCouponDetialAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponResponse.ExpenditureContentBean item, int position) {

    }
}