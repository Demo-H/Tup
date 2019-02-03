package com.tupperware.biz.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.home.HomeIndexBean;
import com.tupperware.biz.ui.activities.BrowserActivity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/13.
 */

public class ZixunHomeAdapter extends BaseQuickAdapter<HomeIndexBean.ItemInfoListBean.ItemContentListBean,BaseViewHolder> {

    public ZixunHomeAdapter(int layoutResId, List<HomeIndexBean.ItemInfoListBean.ItemContentListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_zixun_home_recycleview,null);

        return view;

    }

    @Override
    protected void convert(BaseViewHolder helper, HomeIndexBean.ItemInfoListBean.ItemContentListBean item, int position) {
        helper.setText(R.id.title_text,item.title);
        helper.setText(R.id.author_name , item.author);
        helper.setText(R.id.page_view_count , ""+item.forwardNum + "转发数");
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.content_img);
        simpleDraweeView.setImageURI(item.imgUrl);
//        simpleDraweeView.setImageResource(item.image);
        helper.addOnClickListener(R.id.zixun_item_layout);

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), BrowserActivity.class);
                String url = getData().get(position).link;
                intent.putExtra(Constant.OPEN_URL,url);
                intent.putExtra(Constant.URL_TITLE,"商学院");
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }
}
