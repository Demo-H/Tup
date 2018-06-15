package com.tupperware.huishengyi.adapter;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;
import com.tupperware.huishengyi.utils.SystemUtils;

/**
 * Created by dhunter on 2018/3/21.
 */

public class CouponUnUsedAdapter extends BaseQuickAdapter<BenefitCoinExpenditureBean.ExpenditureContentBean,BaseViewHolder> {

    public CouponUnUsedAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BenefitCoinExpenditureBean.ExpenditureContentBean item, int position) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.coupon_image);
        simpleDraweeView.setImageResource(item.image);
        helper.setText(R.id.date, SystemUtils.getCurrentDate());
        helper.setText(R.id.user_name, item.userName);
        helper.setText(R.id.user_tel, item.userTel);
    }
}
