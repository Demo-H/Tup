package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.msg.MsgBean;
import com.tupperware.biz.ui.activities.BrowserActivity;
import com.tupperware.biz.ui.activities.MessageDetialActivity;
import com.tupperware.biz.utils.DateFormatter;

/**
 * Created by dhunter on 2018/5/22.
 */

public class MessageAdapter extends BaseQuickAdapter<MsgBean.MsgModel, BaseViewHolder> {

    public MessageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MsgBean.MsgModel item, int position) {
        helper.setText(R.id.msg_update, item.name + "（" + item.num + "）");
        if(item.unReadNum > 0) {
            helper.getView(R.id.msg_update_red_tip).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.msg_update_red_tip).setVisibility(View.GONE);
        }
        if(item.messageVO != null) {
            SimpleDraweeView simpleDraweeView = helper.getView(R.id.msg_update_img);
            simpleDraweeView.setImageURI(item.messageVO.imgUrl);
            helper.setText(R.id.msg_update_title, item.messageVO.title);
            helper.setText(R.id.msg_update_content, item.messageVO.subTitle);
            helper.setText(R.id.msg_update_time, DateFormatter.getTimeRange(item.messageVO.createTime));
        }
        helper.getView(R.id.msg_update_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MessageDetialActivity.class);
                intent.putExtra(Constant.MSG_TAG_ID, item.id);
                intent.putExtra(Constant.MSG_TITLE, item.name);
                v.getContext().startActivity(intent);
            }
        });

        helper.getView(R.id.msg_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.messageVO != null) {
                    Intent intent = new Intent(v.getContext(), BrowserActivity.class);
                    String url = item.messageVO.jumpLink;
                    intent.putExtra(Constant.OPEN_URL, url);
                    v.getContext().startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(),"暂无数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
