package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.PurFollowDetialBean;
import com.tupperware.huishengyi.ui.MemberDetialActivity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/30.
 */

public class PurFollowDetialAdapter extends BaseQuickAdapter<PurFollowDetialBean,BaseViewHolder> {

    public PurFollowDetialAdapter(int layoutResId) {
        super(layoutResId);
    }
    public PurFollowDetialAdapter(int layoutResId, List<PurFollowDetialBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurFollowDetialBean item, int position) {
        helper.setText(R.id.user_name, item.userName);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), MemberDetialActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}
