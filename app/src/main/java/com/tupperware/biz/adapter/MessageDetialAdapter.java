package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.msg.MsgItemBean;
import com.tupperware.biz.ui.activities.BrowserActivity;
import com.tupperware.biz.utils.DateFormatter;

/**
 * Created by dhunter on 2018/5/22.
 */

public class MessageDetialAdapter extends BaseQuickAdapter<MsgItemBean.MsgContentBean,BaseViewHolder> {


    public MessageDetialAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgItemBean.MsgContentBean item, int position) {
        if(item != null) {
            helper.setText(R.id.msg_zixun_title, item.title);
            helper.setText(R.id.msg_zixun_content, item.subTitle);
            helper.setText(R.id.msg_zixun_time, DateFormatter.DatetoString(item.createTime));
        }
        helper.addOnClickListener(R.id.item_msg_content_item);

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), BrowserActivity.class);
                String url = getData().get(position).jumpLink;
                intent.putExtra(Constant.OPEN_URL,url);
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }
}
