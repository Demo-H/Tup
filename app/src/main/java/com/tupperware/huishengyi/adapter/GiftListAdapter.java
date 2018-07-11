package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.GiftBean;
import com.tupperware.huishengyi.ui.activities.GiftDetialActivity;
import com.tupperware.huishengyi.utils.StringUtils;

/**
 * Created by dhunter on 2018/6/7.
 */

public class GiftListAdapter extends BaseQuickAdapter<GiftBean.GiftModel, BaseViewHolder> {

    public GiftListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftBean.GiftModel item, int position) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.photo);
        if(item.product_image != null) {
            try {
                String imageUrl = StringUtils.unescapeJava(item.product_image);
                simpleDraweeView.setImageURI(imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        helper.setText(R.id.order_number, item.order_sn);
        helper.setText(R.id.order_status, item.order_status_name);

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
