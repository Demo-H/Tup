package com.tupperware.huishengyi.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.mzBannerView.holder.MZViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.home.HomeIndexBean;

/**
 * Created by dhunter on 2018/3/12.
 */

public class BannerPaddingViewHolder implements MZViewHolder<HomeIndexBean.ItemInfoListBean.ItemContentListBean> {

//    private TextView mTextView;
    private TextView mTextView;
    private SimpleDraweeView mImageView;
    private LinearLayout mLinearlayout;


    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.homerecycle_top_banner_content,null);
        mLinearlayout = (LinearLayout) view.findViewById(R.id.banner_ll);
        mImageView = (SimpleDraweeView) view.findViewById(R.id.sdv_item_fresco_content);
        mTextView = (TextView) view.findViewById(R.id.toutiao_sub_title);
        return view;
    }

    @Override
    public void onBind(Context context, int position, final HomeIndexBean.ItemInfoListBean.ItemContentListBean data) {
        // 数据绑定
        mImageView.setImageURI(data.imgUrl);
//        mImageView.setImageResource(data.models.imgUrl);
        mTextView.setText(data.title);

//        mLinearlayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), data.link, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
