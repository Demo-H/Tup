package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.KeyProductRecordBean;
import com.tupperware.huishengyi.ui.activities.InventoryRecordDetialActivity;

/**
 * Created by dhunter on 2018/3/22.
 */

public class KeyProductRecordInventoryAdapter extends BaseQuickAdapter<KeyProductRecordBean.KeyProductRecordContentBean, BaseViewHolder> {

    public KeyProductRecordInventoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final KeyProductRecordBean.KeyProductRecordContentBean item, int position) {
        helper.setText(R.id.goods_name, item.goodsName);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), InventoryRecordDetialActivity.class);
                intent.putExtra("GoodName", item.goodsName);
                view.getContext().startActivity(intent);
            }
        });
    }
}
