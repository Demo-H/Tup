package com.tupperware.huishengyi.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.ui.activities.LiveDetialActivity;

/**
 * Created by dhunter on 2018/4/18.
 */

public class CollDirectVideoAdapter extends BaseQuickAdapter<CollegeBean.CollegeModel,BaseViewHolder> {


    public CollDirectVideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CollegeBean.CollegeModel bean , int position) {
        helper.setText(R.id.title_text,bean.title);
        helper.setText(R.id.page_view_count , ""+bean.readNum + "人在看");
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.content_img);
        if(position == 0) {
            simpleDraweeView.setImageResource(R.mipmap.info_a_img);
        } else if(position == 1) {
            simpleDraweeView.setImageResource(R.mipmap.info_b_img);
        } else {
            simpleDraweeView.setImageResource(R.mipmap.info_c_img);
        }

        helper.addOnClickListener(R.id.course_item_layout).addOnClickListener(R.id.mark_book);

        setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(mContext, "第"+ position +"页 ", Toast.LENGTH_SHORT).show();
                switch (view.getId()) {
                    case R.id.course_item_layout:
                        Intent intent = new Intent(view.getContext(), LiveDetialActivity.class);
                        intent.putExtra("name","资讯详情");
                        view.getContext().startActivity(intent);
                        break;
                    case R.id.mark_book:
                        Toast.makeText(mContext, "已经订阅" + position, Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });

    }
}