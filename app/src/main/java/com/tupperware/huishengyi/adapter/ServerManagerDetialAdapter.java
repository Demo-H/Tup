package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.huishengyi.entity.PurFollowDetialBean;
import com.tupperware.huishengyi.ui.activities.BackVisitDetialActivity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/30.
 */

public class ServerManagerDetialAdapter extends BaseQuickAdapter<PurFollowDetialBean,BaseViewHolder> {

    public ServerManagerDetialAdapter(int layoutResId) {
        super(layoutResId);
    }
    public ServerManagerDetialAdapter(int layoutResId, List<PurFollowDetialBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurFollowDetialBean item, int position) {
//        helper.setText(R.id.user_name, item.userName);

        setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), BackVisitDetialActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

}