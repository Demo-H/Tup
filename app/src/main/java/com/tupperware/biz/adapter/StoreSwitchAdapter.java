package com.tupperware.biz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.StoreBean;

/**
 * Created by dhunter on 2018/3/19.
 */

public class StoreSwitchAdapter extends BaseQuickAdapter<StoreBean.ContentBean,BaseViewHolder> {

    private int mSelectedPos = -1;//实现单选
    private RecyclerView mRv;

    public StoreSwitchAdapter(int layoutResId, RecyclerView rv) {
        super(layoutResId);
        this.mRv = rv;
    }

    @Override
    protected void convert(final BaseViewHolder helper, StoreBean.ContentBean item, int position) {
        helper.setText(R.id.store_title, item.storeName);
        helper.setBackgroundRes(R.id.store_img, item.image);
        if(item.isSelected) {
            helper.setBackgroundRes(R.id.store_selected, item.isSelectedimage);
            mSelectedPos = position;
        } else {
            helper.setBackgroundRes(R.id.store_selected, item.isNotSelectedimage);
        }


        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(!view.isSelected()) {
                    view.setSelected(true);
                }
            }
        });
    }


}
