package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.RemindMemberInfo;
import com.tupperware.biz.ui.activities.MemberDetialActivity;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.ToastUtil;

/**
 * Created by dhunter on 2018/11/29.
 */

public class RemindAdapter extends BaseQuickAdapter<RemindMemberInfo, BaseViewHolder> {

    public RemindAdapter(int layoutResId) {
        super(layoutResId);

        setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), MemberDetialActivity.class);
                if(getData().get(position).getMemberId() == 0) {
                    ToastUtil.showS("会员已不存在");
                } else {
                    intent.putExtra(Constant.MEMBER_ID, getData().get(position).getMemberId());
                    view.getContext().startActivity(intent);
                }
                return false;
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, RemindMemberInfo item, int position) {
        helper.setText(R.id.name, item.getName());
        /**
         * 1高级会员 0普通会员 2 粉丝
         */
        ImageView mVipTip = helper.getView(R.id.member_type);
        if(item.getGroupId() == 0) {
            if(mVipTip.getVisibility() == View.GONE) {
                mVipTip.setVisibility(View.VISIBLE);
            }
            mVipTip.setImageResource(R.mipmap.mbr_meb_ic);
        } else if(item.getGroupId() == 1){
            if(mVipTip.getVisibility() == View.GONE) {
                mVipTip.setVisibility(View.VISIBLE);
            }
            mVipTip.setImageResource(R.mipmap.mbr_hmeb_ic);
        } else if(item.getGroupId() == 2){
            if(mVipTip.getVisibility() == View.GONE) {
                mVipTip.setVisibility(View.VISIBLE);
            }
            mVipTip.setImageResource(R.mipmap.mbr_fans_ic);
        } else {
            if(mVipTip.getVisibility() == View.VISIBLE) {
                mVipTip.setVisibility(View.GONE);
            }
        }
//        String mobile = item.getMobile();
//        if(CheckUtils.isPhoneNumber(mobile)) {
//            helper.setText(R.id.tel_num, "+86 " + item.getMobile());
//        }
        helper.setText(R.id.tel_num, item.getMobile());
        helper.setText(R.id.join_in_time, "登记时间：" + DateFormatter.timeSecondToDateDay(item.getRegTime()));

        helper.addOnClickListener(R.id.members_item);
    }
}
