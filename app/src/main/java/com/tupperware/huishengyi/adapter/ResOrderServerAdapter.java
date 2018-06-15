package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.ResOrderPendingBean;
import com.tupperware.huishengyi.ui.ResOrderServerDetialActivity;

/**
 * Created by dhunter on 2018/4/8.
 */

public class ResOrderServerAdapter extends BaseQuickAdapter<ResOrderPendingBean.OrderContentBean,BaseViewHolder> {

    private int mTabPosition;

    public ResOrderServerAdapter(int layoutResId, int mTabPosition) {
        super(layoutResId);
        this.mTabPosition = mTabPosition;
    }

    @Override
    protected void convert(BaseViewHolder helper, ResOrderPendingBean.OrderContentBean item, int position) {

        helper.addOnClickListener(R.id.order_server_item_layout);

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), ResOrderServerDetialActivity.class);
                view.getContext().startActivity(intent);
                return false;
            }
        });

    }
}
