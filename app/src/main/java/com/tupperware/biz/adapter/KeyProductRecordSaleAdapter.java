package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.KeyProductRecordBean;
import com.tupperware.biz.ui.activities.SaleRecordDetialActivity;

/**
 * Created by dhunter on 2018/3/22.
 */

public class KeyProductRecordSaleAdapter extends BaseQuickAdapter<KeyProductRecordBean.KeyProductRecordContentBean, BaseViewHolder> {

    public KeyProductRecordSaleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final KeyProductRecordBean.KeyProductRecordContentBean item, int position) {
        helper.setText(R.id.goods_name, item.goodsName);


        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), SaleRecordDetialActivity.class);
                intent.putExtra("GoodName", item.goodsName);
                view.getContext().startActivity(intent);
            }
        });
    }
}
