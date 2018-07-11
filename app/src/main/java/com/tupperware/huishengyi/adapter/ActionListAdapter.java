package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.StoreScheduleBean;
import com.tupperware.huishengyi.ui.activities.ActionInvitateActivity;
import com.tupperware.huishengyi.ui.activities.BrowserActivity;

/**
 * Created by dhunter on 2018/6/7.
 */

public class ActionListAdapter extends BaseQuickAdapter<StoreScheduleBean.ActionModel, BaseViewHolder> {

    private String activity_from; //通过该flag来标记跳转

    public ActionListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreScheduleBean.ActionModel item, int position) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.action_image);
        simpleDraweeView.setImageURI(item.getImgUrl());

        helper.addOnClickListener(R.id.action_item);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(Constant.PERSONAL_SETTING.equals(activity_from)) {
                    Intent intent = new Intent(view.getContext(), BrowserActivity.class);
                    intent.putExtra(Constant.OPEN_URL, getData().get(position).getLink());
                    intent.putExtra(Constant.URL_TITLE, getData().get(position).getTitle());
                    view.getContext().startActivity(intent);
                } else if(Constant.LOVE_VIP_FRAGMENT.equals(activity_from)) {
                    Intent act_intent = new Intent(view.getContext(), ActionInvitateActivity.class);
                    act_intent.putExtra(Constant.ACTION_MEMBER_INFO_ID, getData().get(position).getInfoId());
                    act_intent.putExtra(Constant.ACTION_MEMBER_STORE_ID, getData().get(position).getStoreId());
                    view.getContext().startActivity(act_intent);
                }
                return false;
            }
        });
    }

    public void setActivity_from(String activity_from) {
        this.activity_from = activity_from;
    }
}
