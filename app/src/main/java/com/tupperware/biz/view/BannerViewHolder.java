package com.tupperware.biz.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.android.dhunter.common.widget.mzBannerView.holder.MZViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.college.CollegeBean;

/**
 * Created by dhunter on 2018/4/17.
 */

public class BannerViewHolder implements MZViewHolder<CollegeBean.CollegeModel> {
    private SimpleDraweeView mImageView;
    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner_item,null);
        mImageView = (SimpleDraweeView) view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, CollegeBean.CollegeModel mBean) {
        // 数据绑定
//        mImageView.setImageResource(data);
        mImageView.setImageURI(mBean.imgUrl);
    }
}