package com.tupperware.biz.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.GiftBean;
import com.tupperware.biz.ui.activities.GiftDetialActivity;
import com.tupperware.biz.utils.StringUtils;

/**
 * Created by dhunter on 2018/6/7.
 */

public class GiftListAdapter extends BaseQuickAdapter<GiftBean.GiftInfo, BaseViewHolder> {

    public GiftListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftBean.GiftInfo item, int position) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.photo);
        if(item.getProductImage() != null) {
            try {
                String imageUrl = StringUtils.unescapeJava(item.getProductImage());
                simpleDraweeView.setImageURI(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        helper.setText(R.id.order_number, item.getOrderSn());
        helper.setText(R.id.order_status, item.getOrderStatusName());

        helper.addOnClickListener(R.id.gift_item);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), GiftDetialActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.MEMBER_GIFT_DATA, getData().get(position));
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
                return false;
            }
        });

    }
}
