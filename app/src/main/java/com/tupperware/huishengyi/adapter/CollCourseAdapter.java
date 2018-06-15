package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.ui.CourseDetialActivity;

/**
 * Created by dhunter on 2018/4/17.
 */

public class CollCourseAdapter extends BaseQuickAdapter<CollegeBean.CollegeModel,BaseViewHolder> {


    public CollCourseAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CollegeBean.CollegeModel bean , int position) {
        helper.setText(R.id.title_text,bean.title);
        helper.setText(R.id.page_view_count , ""+bean.learnNum + "人在学");
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.content_img);
        simpleDraweeView.setImageURI(bean.imgUrl);

        helper.addOnClickListener(R.id.course_item_layout);

        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), CourseDetialActivity.class);
                intent.putExtra(Constant.COURSE_ID, getData().get(position).id);
                view.getContext().startActivity(intent);
                return false;
            }
        });
    }
}