package com.tupperware.biz.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.view.MemberSortSelectPopup;

import java.util.ArrayList;

/**
 * Created by dhunter on 2018/10/26.
 */

public class MemberPopupAdapter extends BaseAdapter {
    private Context myContext;
    private LayoutInflater inflater;
    private ArrayList<String> mItems;
    private String mSelectItemStr;
    private int mLastCheckedPosition = 0;//实现单选
    private SparseBooleanArray mBooleanArray;
    private MemberSortSelectPopup.OnSortSelectedClickListener mOnSortSelectedListener;

    public MemberPopupAdapter(Context context, ArrayList<String> items) {
        this.myContext = context;
        this.mItems = items;
        inflater = LayoutInflater.from(myContext);
        mBooleanArray = new SparseBooleanArray(items.size());
    }

    /**
     *
     * @param context
     * @param items
     * @param string  被选中的item
     */
    public MemberPopupAdapter(Context context, ArrayList<String> items, String string, MemberSortSelectPopup.OnSortSelectedClickListener mOnSortSelectedListener) {
        this.myContext = context;
        this.mItems = items;
        this.mSelectItemStr = string;
        this.mOnSortSelectedListener = mOnSortSelectedListener;
        inflater = LayoutInflater.from(myContext);
        mBooleanArray = new SparseBooleanArray(items.size());

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PopupHolder holder = null;
        if (convertView == null) {
            holder = new PopupHolder();
            convertView = inflater.inflate(R.layout.member_popup_item, null);
            holder.mItemrl = (RelativeLayout) convertView.findViewById(R.id.sort_selected_item);
            holder.itemNameTv = (TextView) convertView
                    .findViewById(R.id.popup_tv);
            holder.selectIcon = (ImageView) convertView.findViewById(R.id.sort_selected_icon);
            convertView.setTag(holder);
        } else {
            holder = (PopupHolder) convertView.getTag();
        }
        String itemName = getItem(position);
        if(itemName != null && itemName.equals(mSelectItemStr)) {
            holder.itemNameTv.setTextColor(0xffff7700);
            holder.selectIcon.setSelected(true);
            mBooleanArray.put(position, true); //默认选中
            mLastCheckedPosition = position;
        } else {
            holder.itemNameTv.setTextColor(0xff9b9b9b);
            holder.selectIcon.setSelected(false);
        }
        holder.itemNameTv.setText(itemName);
//        holder.mItemrl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setItemChecked(position);
//                mOnSortSelectedListener.onSortSelectedClick(position);
//            }
//        });
        return convertView;
    }

    private class PopupHolder {
        TextView itemNameTv;
        ImageView selectIcon;
        RelativeLayout mItemrl;
    }

    public void setItemChecked(int position) {
        if (mLastCheckedPosition == position)
            return;
        mBooleanArray.put(position, true);
        mBooleanArray.put(mLastCheckedPosition, false);
        notifyDataSetChanged();
        mLastCheckedPosition = position;
    }

}