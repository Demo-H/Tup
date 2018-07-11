package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.ui.activities.BrowserActivity;

/**
 * Created by dhunter on 2018/4/17.
 */

public class CollArticleAdapter extends BaseQuickAdapter<CollegeBean.CollegeModel,BaseViewHolder> {


    public CollArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CollegeBean.CollegeModel bean , int position) {
        helper.setText(R.id.title_text,bean.title);
        helper.setText(R.id.page_view_count , ""+bean.readNum + "人在看"); //显示作者
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.content_img);
        simpleDraweeView.setImageURI(bean.imgUrl);
//        if(position == 0) {
//            simpleDraweeView.setImageResource(R.mipmap.info_a_img);
//        } else if(position == 1) {
//            simpleDraweeView.setImageResource(R.mipmap.info_b_img);
//        } else {
//            simpleDraweeView.setImageResource(R.mipmap.info_c_img);
//        }

        helper.addOnClickListener(R.id.course_item_layout);

        setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), BrowserActivity.class);
                String url = getData().get(position).link;
                intent.putExtra(Constant.OPEN_URL,url);
                intent.putExtra(Constant.URL_TITLE,"精选资讯");
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }
}