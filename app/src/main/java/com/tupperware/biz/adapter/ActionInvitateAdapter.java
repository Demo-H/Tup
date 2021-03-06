package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.ReservationItem;
import com.tupperware.biz.ui.activities.ReservationActionActivity;

/**
 * Created by dhunter on 2018/6/14.
 */

public class ActionInvitateAdapter extends BaseQuickAdapter<ReservationItem, BaseViewHolder> {

    public ActionInvitateAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ReservationItem item, int position) {
        helper.setText(R.id.user_name, item.getName());
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.photo);
        simpleDraweeView.setImageURI(item.getImgUrl());

        setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), ReservationActionActivity.class);
                intent.putExtra(Constant.ACTION_MEMBER_ID, item.getId());
                view.getContext().startActivity(intent);
            }
        });
    }
}