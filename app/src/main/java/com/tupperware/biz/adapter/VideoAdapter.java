package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.VideoBean;
import com.tupperware.biz.ui.activities.BrowserActivity;

/**
 * Created by dhunter on 2018/3/6.
 */

public class VideoAdapter extends BaseQuickAdapter<VideoBean.ContentBean,BaseViewHolder> {

    public VideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoBean.ContentBean bean ,int position) {
        helper.setText(R.id.title_text,bean.title);
        helper.setText(R.id.author_name , bean.authorName);
        helper.setText(R.id.page_view_count , ""+bean.pageView + "转发数");
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.content_img);
//        simpleDraweeView.setImageURI(bean.indexImage);
        simpleDraweeView.setImageResource(R.mipmap.biz_video_img);
        helper.addOnClickListener(R.id.video_item_layout);

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(mContext, "第"+ position +"页 ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), BrowserActivity.class);
                intent.putExtra(Constant.OPEN_URL,"https://mp.weixin.qq.com/s?__biz=MzA5MDg2NjYyNg==&mid=2658636076&idx=1&sn=c79396903ce298a1108b7435688c6c10&mpshare=1&scene=1&srcid=0305gtWkmiECA6LOIVkm8qeB#rd");
                intent.putExtra(Constant.URL_TITLE,"视频详情");
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }
}
