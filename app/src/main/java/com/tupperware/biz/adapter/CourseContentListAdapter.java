package com.tupperware.biz.adapter;

import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.View;

import com.android.dhunter.common.baserecycleview.BaseMultiItemQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.biz.R;
import com.tupperware.biz.entity.college.CourseContentBean;
import com.tupperware.biz.listener.ICourseVideoListener;

/**
 * Created by dhunter on 2018/5/10.
 */

public class CourseContentListAdapter extends BaseMultiItemQuickAdapter<CourseContentBean, BaseViewHolder> {

    private ICourseVideoListener mListener;
    private SparseBooleanArray mBooleanArray;
    private int mLastCheckedPosition = -1;
    public CourseContentListAdapter(ICourseVideoListener mListener) {
        super();
        this.mListener = mListener;
        addItemType(CourseContentBean.HEADER, R.layout.item_course_detial_header);
        addItemType(CourseContentBean.CONTENT, R.layout.item_course_detial_content);
        addItemType(CourseContentBean.FOOTER, R.layout.item_course_detial_footer);
        mBooleanArray = new SparseBooleanArray(getData().size());
    }

    @Override
    protected void convert(BaseViewHolder helper, final CourseContentBean item, int position) {
        switch (item.itemType) {
            case CourseContentBean.HEADER:
                helper.setText(R.id.chapter_title, item.chapter);
                break;
            case CourseContentBean.CONTENT:
                String section = String.format("%02d",item.section);
                helper.setText(R.id.section, "课时" + section);
                helper.setText(R.id.sub_chapter_title, item.chapter);
                if(item.filePath.endsWith("ppt")) {
                    helper.getView(R.id.style_show).setBackgroundResource(R.mipmap.bs_pic_btn);
                } else {
                    helper.getView(R.id.style_show).setBackgroundResource(R.mipmap.bs_play_s_btn);
                }
                if (mBooleanArray.get(helper.getAdapterPosition())) {
                    helper.setTextColor(R.id.section, Color.parseColor("#ff7000"));
                    helper.setTextColor(R.id.sub_chapter_title, Color.parseColor("#ff7000"));
                } else {
                    helper.setTextColor(R.id.section, Color.parseColor("#43484b"));
                    helper.setTextColor(R.id.sub_chapter_title, Color.parseColor("#43484b"));
                }
                helper.addOnClickListener(R.id.item_course_content);
                setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        setItemChecked(position);
                        mListener.itemClickListener(getData().get(position).fileName, getData().get(position).filePath);
                        return false;
                    }
                });
                break;
            case CourseContentBean.FOOTER:
                break;
        }

//        helper.setOnClickListener(R.id.style_show, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.itemClickListener(item.filePath);
//            }
//        });
    }

    public void setItemChecked(int position) {
        if (mLastCheckedPosition == position)
            return;

        mBooleanArray.put(position, true);

        if (mLastCheckedPosition > -1) {
            mBooleanArray.put(mLastCheckedPosition, false);
            notifyItemChanged(mLastCheckedPosition);
        }
        notifyDataSetChanged();
        mLastCheckedPosition = position;
    }


}
