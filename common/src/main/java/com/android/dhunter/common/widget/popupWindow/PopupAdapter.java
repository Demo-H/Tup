package com.android.dhunter.common.widget.popupWindow;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.dhunter.common.R;

import java.util.ArrayList;

/**
 * Created by dhunter on 2018/3/13.
 */

public class PopupAdapter extends BaseAdapter {
    private Context myContext;
    private LayoutInflater inflater;
    private ArrayList<String> mItems;
    private String mType;
    private String mSelectItemStr;

    public PopupAdapter(Context context, ArrayList<String> items, String type) {
        this.myContext = context;
        this.mItems = items;
        this.mType = type;

        inflater = LayoutInflater.from(myContext);

    }

    /**
     *
     * @param context
     * @param items
     * @param type
     * @param string  被选中的item
     */
    public PopupAdapter(Context context, ArrayList<String> items, String type, String string) {
        this.myContext = context;
        this.mItems = items;
        this.mType = type;
        this.mSelectItemStr = string;

        inflater = LayoutInflater.from(myContext);

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
    public View getView(int position, View convertView, ViewGroup parent) {
        PopupHolder holder = null;
        if (convertView == null) {
            holder = new PopupHolder();
            convertView = inflater.inflate(R.layout.top_popup_item, null);
            holder.itemNameTv = (TextView) convertView
                    .findViewById(R.id.popup_tv);
            if (mType.equals(Config.RIGHT)) {
                holder.itemNameTv.setGravity(Gravity.RIGHT);
            } else if (mType.equals(Config.LEFT)) {
                holder.itemNameTv.setGravity(Gravity.LEFT);
            } else if (mType.equals(Config.MIDDLE)) {
                holder.itemNameTv.setGravity(Gravity.CENTER);
            }
            convertView.setTag(holder);
        } else {
            holder = (PopupHolder) convertView.getTag();
        }
        String itemName = getItem(position);
        if(itemName != null && itemName.equals(mSelectItemStr)) {
            holder.itemNameTv.setTextColor(0xffff7700);
        } else {
            holder.itemNameTv.setTextColor(0xff9b9b9b);
        }
        holder.itemNameTv.setText(itemName);
        return convertView;
    }

    private class PopupHolder {
        TextView itemNameTv;
    }
}
