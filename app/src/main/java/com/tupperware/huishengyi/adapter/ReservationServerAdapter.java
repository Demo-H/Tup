package com.tupperware.huishengyi.adapter;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.member.ReservationItem;

/**
 * Created by dhunter on 2018/5/31.
 */

public class ReservationServerAdapter extends BaseQuickAdapter<ReservationItem, BaseViewHolder> {

    public ReservationServerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReservationItem item, int position) {
        helper.setText(R.id.server_title, item.getTitle());
        helper.setText(R.id.server_host_name, item.getName());
        helper.setText(R.id.contact_tel, item.getMobile());
        helper.setText(R.id.join_num, item.getNum() + "");
        helper.setText(R.id.delicacy, item.getIntention());
        helper.setText(R.id.play_times, item.getSessionTime());
    }
}
