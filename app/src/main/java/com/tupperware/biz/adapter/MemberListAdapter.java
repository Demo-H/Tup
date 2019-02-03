package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.ui.activities.MemberDetialActivity;
import com.tupperware.biz.utils.DateFormatter;

/**
 * Created by dhunter on 2018/10/22.
 */

public class MemberListAdapter extends BaseQuickAdapter<MemberBean.MemberInfo, BaseViewHolder> {

    public MemberListAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, MemberBean.MemberInfo item, int position) {

        helper.setText(R.id.name, item.getName());
        ImageView sexFlag = helper.getView(R.id.sex_flag);
        /**
         * 设置性别图标
         */
        if(item.getGender() == 0) {
            if(sexFlag.getVisibility() == View.GONE) {
                sexFlag.setVisibility(View.VISIBLE);
            }
            sexFlag.setBackgroundResource(R.mipmap.meb_male_ic);
        } else if(item.getGender() == 1) {
            if(sexFlag.getVisibility() == View.GONE) {
                sexFlag.setVisibility(View.VISIBLE);
            }
            sexFlag.setBackgroundResource(R.mipmap.meb_female_ic);
        } else {
            if(sexFlag.getVisibility() == View.VISIBLE) {
                sexFlag.setVisibility(View.GONE);
            }
        }
        /**
         * 1高级会员 0普通会员 2 粉丝
         */
        ImageView mVipTip = helper.getView(R.id.member_type);
        TextView mGiftStatus = helper.getView(R.id.gift_status);
        if(item.getGroup_id() == 0) {
            if(mVipTip.getVisibility() == View.GONE) {
                mVipTip.setVisibility(View.VISIBLE);
            }
            mVipTip.setImageResource(R.mipmap.mbr_meb_ic);
            if(mGiftStatus.getVisibility() == View.VISIBLE) {
                mGiftStatus.setVisibility(View.GONE);
            }
        } else if(item.getGroup_id() == 1){
            if(mVipTip.getVisibility() == View.GONE) {
                mVipTip.setVisibility(View.VISIBLE);
            }
            mVipTip.setImageResource(R.mipmap.mbr_hmeb_ic);
            if(mGiftStatus.getVisibility() == View.GONE) {
                mGiftStatus.setVisibility(View.VISIBLE);
            }
            mGiftStatus.setText(item.getGift_apply_status());
        } else if(item.getGroup_id() == 2){
            if(mVipTip.getVisibility() == View.GONE) {
                mVipTip.setVisibility(View.VISIBLE);
            }
            mVipTip.setImageResource(R.mipmap.mbr_fans_ic);
            if(mGiftStatus.getVisibility() == View.VISIBLE) {
                mGiftStatus.setVisibility(View.GONE);
            }
        } else {
            if(mVipTip.getVisibility() == View.VISIBLE) {
                mVipTip.setVisibility(View.GONE);
            }
            if(mGiftStatus.getVisibility() == View.VISIBLE) {
                mGiftStatus.setVisibility(View.GONE);
            }
        }
        helper.setText(R.id.tel_num, item.getMobile());
        helper.setText(R.id.join_in_time, "入会时间：" + DateFormatter.timeSecondToDateDay(item.getReg_time()));
        helper.setText(R.id.income, "收入：" + item.getIntegral_total());
        helper.setText(R.id.balance, "余额：" + item.getIntegral_amount());

        helper.addOnClickListener(R.id.members_item);

        setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), MemberDetialActivity.class);
                intent.putExtra(Constant.MEMBER_ID, getData().get(position).getMember_id());
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }
}