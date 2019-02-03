package com.tupperware.biz.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseMultiItemQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.entity.LoveVipIndex;
import com.tupperware.biz.listener.PositionChangedListener;

/**
 * Created by dhunter on 2018/3/8.
 */

public class LoveVipRecycleAdapter extends BaseMultiItemQuickAdapter<LoveVipIndex.ItemInfoListBean, BaseViewHolder> implements BaseQuickAdapter.SpanSizeLookup, BaseQuickAdapter.OnItemChildClickListener {

    /**
     * 当前position监听
     */
    private PositionChangedListener listener;

    public LoveVipRecycleAdapter() {
        setSpanSizeLookup(this);

    }

    public void setListener(PositionChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return 0;
    }

    @Override
    protected void convert(BaseViewHolder helper, LoveVipIndex.ItemInfoListBean item, int position) {

    }

    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
