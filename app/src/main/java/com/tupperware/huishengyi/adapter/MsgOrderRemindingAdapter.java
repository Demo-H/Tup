package com.tupperware.huishengyi.adapter;

import android.view.View;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.msg.MsgItemBean;

/**
 * Created by dhunter on 2018/3/26.
 */

public class MsgOrderRemindingAdapter extends BaseQuickAdapter<MsgItemBean.MsgContentBean,BaseViewHolder> {


    private boolean isshowBox = false;

    public MsgOrderRemindingAdapter(int layoutResId) {
        super(layoutResId);

    }

    @Override
    protected void convert(BaseViewHolder helper, MsgItemBean.MsgContentBean item, int position) {
        if(isshowBox) {
            helper.getView(R.id.msg_list_check_rl).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.msg_list_check_rl).setVisibility(View.GONE);
        }
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
