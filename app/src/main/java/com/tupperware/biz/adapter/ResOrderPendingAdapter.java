package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.order.OrderBean;
import com.tupperware.biz.ui.activities.OnlineOrderDetialActivity;

/**
 * Created by dhunter on 2018/3/15.
 */

public class ResOrderPendingAdapter extends BaseQuickAdapter<OrderBean.OrderModel,BaseViewHolder> {

    private int mTabPosition;

    public ResOrderPendingAdapter(int layoutResId, int mTabPosition) {
        super(layoutResId);
        this.mTabPosition = mTabPosition;
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

        if(mTabPosition == 0) {
            helper.getView(R.id.date_of_delivery).setVisibility(View.GONE);
            helper.getView(R.id.call_contacts).setVisibility(View.GONE);
//            helper.getView(R.id.call_contacts).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "电话联系功能暂未开通",Toast.LENGTH_SHORT).show();
//                }
//            });
        } else if (mTabPosition == 1) {
            helper.getView(R.id.call_contacts).setVisibility(View.VISIBLE);
            helper.getView(R.id.date_of_delivery).setVisibility(View.GONE);
            helper.setText(R.id.call_contacts, "催发货");
            helper.getView(R.id.call_contacts).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "已提醒发货",Toast.LENGTH_SHORT).show();
                }
            });
        } else if (mTabPosition == 2) {
            helper.getView(R.id.call_contacts).setVisibility(View.GONE);
            helper.getView(R.id.date_of_delivery).setVisibility(View.VISIBLE);
        } else if (mTabPosition == 3) {
            helper.getView(R.id.call_contacts).setVisibility(View.GONE);
            helper.getView(R.id.date_of_delivery).setVisibility(View.VISIBLE);
            helper.setText(R.id.date_of_delivery, "已完成");
        } else if (mTabPosition == 4) {
            helper.getView(R.id.call_contacts).setVisibility(View.VISIBLE);
            helper.getView(R.id.date_of_delivery).setVisibility(View.GONE);
            helper.setText(R.id.call_contacts, "退款中");
            helper.getView(R.id.call_contacts).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "已提醒退款",Toast.LENGTH_SHORT).show();
                }
            });
        } else if (mTabPosition == 5) {
            helper.getView(R.id.call_contacts).setVisibility(View.GONE);
            helper.getView(R.id.date_of_delivery).setVisibility(View.VISIBLE);
            helper.setText(R.id.date_of_delivery, "已关闭");
        }


        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), OnlineOrderDetialActivity.class);
                intent.putExtra(Constant.POSITION, mTabPosition);
                intent.putExtra(Constant.ORDER_ID, getData().get(position).id);
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }
}
