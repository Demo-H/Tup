package com.tupperware.biz.adapter;

import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.msg.MsgItemBean;

/**
 * Created by dhunter on 2018/3/26.
 */

public class MsgZixunUpdateAdapter extends BaseQuickAdapter<MsgItemBean.MsgContentBean,BaseViewHolder> {

    private boolean isshowBox = false;

    public MsgZixunUpdateAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, MsgItemBean.MsgContentBean item, int position) {
        if(isshowBox) {
            helper.getView(R.id.msg_list_check_rl).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.msg_list_check_rl).setVisibility(View.GONE);
        }

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int positions) {
        super.onBindViewHolder(holder, positions);
    }

    public void setShowBox() {
        //取反
        isshowBox = !isshowBox;
    }
}
