package com.tupperware.biz.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.utils.ScreenUtil;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseApplication;
import com.tupperware.biz.entity.TagBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhunter on 2018/3/9.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private List<TagBean> tagList;

    private boolean isSelected = false;

    private List<TagBean> selectList;
    private RelativeLayout mRelativeLayout;
    private int emptyviewtype = 100;

    public TagAdapter(List<TagBean> tagList) {
        this.tagList = tagList;
        selectList = new ArrayList<>();
    }

    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_layout, parent, false);
//        if(viewType == emptyviewtype) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_empty_layout, parent, false);
//        } else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_layout, parent, false);
//        }

//        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_cyviewhold);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final TagAdapter.ViewHolder holder, final int position) {
        holder.mTextView.setText(tagList.get(position).getTag_name());
        holder.mCount.setText(tagList.get(position).getCount());
        holder.itemView.setTag(tagList.get(position));
        /*holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected = !holder.mTextView.isSelected();
                if (isSelected) {
                    holder.mTextView.setSelected(true);
                    holder.mTextView.setBackgroundResource(R.drawable.tag_checked_bg);
                    selectList.add(tagList.get(position));
                } else {
                    holder.mTextView.setSelected(false);
                    holder.mTextView.setBackgroundResource(R.drawable.tag_normal_bg);
                    selectList.remove(tagList.get(position));
                }
            }
        });*/

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
//        if(position == 9) {
//            return emptyviewtype;
//        } else {
//            return super.getItemViewType(position);
//        }
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mViewHolder;
        public TextView mTextView;
        public TextView mCount;

        public ViewHolder(View view) {
            super(view);
            mViewHolder = (RelativeLayout) view.findViewById(R.id.rl_cyviewhold);
            mTextView = (TextView) view.findViewById(R.id.tag_tv);
            mCount = (TextView) view.findViewById(R.id.count);
            LinearLayout.LayoutParams Params =  (LinearLayout.LayoutParams)mViewHolder.getLayoutParams();
            //为单独一行的view偏移对应间隔的距离6dp
            Params.width = ScreenUtil.getWidth(BaseApplication.getInstance().getApplicationContext()) / 2 - 6;
            mViewHolder.setLayoutParams(Params);

        }
    }

    public List<TagBean> getSelectData(){
        return selectList;
    }
}
