package com.tupperware.biz.adapter;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.benefit.CouponInfo;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.ImageUrl;

/**
 * Created by dhunter on 2018/3/21.
 */

public class CouponUnUsedAdapter extends BaseQuickAdapter<CouponInfo, BaseViewHolder> {

    private int mTabPosition;

    public CouponUnUsedAdapter(int layoutResId, int tabPosition) {
        super(layoutResId);
        this.mTabPosition = tabPosition;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponInfo item, int position) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.coupon_image);
        if(item.getImageurl() != null && !item.getImageurl().isEmpty()) {
            String url = ImageUrl.parseUrl(item.getImageurl());
            simpleDraweeView.setImageURI(url);
        }
//        if(mTabPosition == 0 ){
//            helper.setText(R.id.date, DateFormatter.timesecondToDate(item.getAvailableStartTime()) + "-" + DateFormatter.timesecondToDate(item.getAvailableEndTime()));
//        } else {
            helper.setText(R.id.date, DateFormatter.timesecondToDate(item.getUsedTime()));
//        }
        helper.setText(R.id.user_name, item.getRedundantUsedMemberName());
        helper.setText(R.id.user_tel, item.getRedundantUsedMemberMobile());
    }
}
