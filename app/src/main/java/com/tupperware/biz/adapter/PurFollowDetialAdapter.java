package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.PurFollowDetialBean;
import com.tupperware.biz.ui.activities.MemberDetialActivity;

/**
 * Created by dhunter on 2018/3/30.
 */

public class PurFollowDetialAdapter extends BaseQuickAdapter<PurFollowDetialBean.PageInfo.PurFollowMember, BaseViewHolder> {

    public PurFollowDetialAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurFollowDetialBean.PageInfo.PurFollowMember item, int position) {
        if(item.getMemberName() == null || item.getMemberName().isEmpty()) {
            helper.setText(R.id.user_name, item.getMemberMobile());
        } else {
            helper.setText(R.id.user_name, item.getMemberName());
        }

        SimpleDraweeView simpleDraweeView = helper.getView(R.id.photo);
        simpleDraweeView.setImageURI(item.getHeadimgurl());

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), MemberDetialActivity.class);
                String mobilephone = getData().get(position).getMemberMobile();
                if(mobilephone != null) {
                    intent.putExtra(Constant.DEV_MEMBER_PHONE, mobilephone);
                } else {
                    intent.putExtra(Constant.MEMBER_ID, getData().get(position).getMemberId());
                }
                view.getContext().startActivity(intent);
            }
        });
    }
}
