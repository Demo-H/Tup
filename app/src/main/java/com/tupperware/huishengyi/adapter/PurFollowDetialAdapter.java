package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.PurFollowDetialBean;
import com.tupperware.huishengyi.ui.activities.MemberDetialActivity;

/**
 * Created by dhunter on 2018/3/30.
 */

public class PurFollowDetialAdapter extends BaseQuickAdapter<PurFollowDetialBean.PageInfo.PurFollowMember, BaseViewHolder> {

    public PurFollowDetialAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurFollowDetialBean.PageInfo.PurFollowMember item, int position) {
        if(item.getUserName().isEmpty()) {
            helper.setText(R.id.user_name, item.getNickname());
        } else {
            helper.setText(R.id.user_name, item.getUserName());
        }

        SimpleDraweeView simpleDraweeView = helper.getView(R.id.photo);
        simpleDraweeView.setImageURI(item.getHeadimgurl());

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), MemberDetialActivity.class);
                intent.putExtra(Constant.DEV_MEMBER_PHONE, getData().get(position).getMobile());
                view.getContext().startActivity(intent);
            }
        });
    }
}
