package com.tupperware.biz.adapter;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.order.OrderItemBean;

/**
 * Created by dhunter on 2018/4/9.
 */

public class OnlineOrderDetialAdapter extends BaseQuickAdapter<OrderItemBean.OrderItemModel.GoodsModel, BaseViewHolder> {

    public OnlineOrderDetialAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderItemBean.OrderItemModel.GoodsModel item, int position) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.product_img);
        simpleDraweeView.setImageURI(item.img);
        helper.getView(R.id.product_img).setBackgroundResource(R.mipmap.coupon_prd_pic);
        helper.setText(R.id.product_des, item.name);
        helper.setText(R.id.product_unit_price, "￥" + item.price);
        helper.setText(R.id.product_num, "x" + item.num);

    }
}
