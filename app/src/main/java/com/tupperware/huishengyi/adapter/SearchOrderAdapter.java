package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.order.OrderBean;
import com.tupperware.huishengyi.ui.OnlineOrderDetialActivity;

/**
 * Created by dhunter on 2018/5/23.
 */

public class SearchOrderAdapter extends BaseQuickAdapter<OrderBean.OrderModel,BaseViewHolder> {

    public SearchOrderAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, OrderBean.OrderModel item, int position) {
        helper.setText(R.id.order_number, item.code);

        SimpleDraweeView simpleDraweeView = helper.getView(R.id.product_img);
        simpleDraweeView.setImageURI(item.goods.img);
//        simpleDraweeView.setImageResource(R.mipmap.order_prd_img);
        helper.setText(R.id.product_des, item.goods.name);
        helper.setText(R.id.product_unit_price, "￥" + item.totalMny);
        String goodNumber = "共" + item.goodsNum +"件商品，需付款";
        helper.setText(R.id.order_num_pending, goodNumber);
        helper.setText(R.id.order_pending_money_num, "￥" + item.realMny);
        helper.addOnClickListener(R.id.order_pending_item_layout);

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), OnlineOrderDetialActivity.class);
                intent.putExtra(Constant.ORDER_ID, getData().get(position).id);
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }
}
