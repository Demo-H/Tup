package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.MemberBean;
import com.tupperware.huishengyi.ui.MemberDetialActivity;

/**
 * Created by dhunter on 2018/5/23.
 */

public class SearchMemberAdapter extends BaseQuickAdapter<MemberBean.MemberInfo, BaseViewHolder> {

    public SearchMemberAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, MemberBean.MemberInfo item, int position) {

        helper.setText(R.id.name, item.name);
        /**
         * 1高级会员 0普通会员 2 全部
         */
        ImageView mVipTip = helper.getView(R.id.vip_tip);
        if(item.group_id == 1) {
            mVipTip.setVisibility(View.VISIBLE);
        } else {
            mVipTip.setVisibility(View.GONE);
        }

        helper.setText(R.id.tel_num, item.mobile);

        helper.addOnClickListener(R.id.search_member_item);

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), MemberDetialActivity.class);
                intent.putExtra(Constant.MEMBER_ID, getData().get(position).member_id);
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }
}