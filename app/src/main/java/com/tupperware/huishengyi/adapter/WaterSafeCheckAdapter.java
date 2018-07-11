package com.tupperware.huishengyi.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.entity.DataEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dhunter on 2018/3/29.
 */

public class WaterSafeCheckAdapter extends BaseQuickAdapter<DataEntry,BaseViewHolder> {

    public HashMap<Integer, Boolean> map;
    private List<DataEntry> selected;

    public WaterSafeCheckAdapter(int layoutResId, List<DataEntry> data) {
        super(layoutResId, data);
        map = new HashMap<>();
        selected = new ArrayList<>();
        for (int i = 0, p = data.size(); i < p; i++) {
            map.put(i, false);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, DataEntry item, int position) {
        helper.setText(R.id.title, item.title);
        helper.setChecked(R.id.select, map.get(position));

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.select);
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_water_safe_check_recycleview,null);

        return view;

    }

    public HashMap<Integer, Boolean> getMap() {
        return map;
    }

    public void setMap(HashMap<Integer, Boolean> map) {
        this.map = map;
        notifyDataSetChanged();
    }

    public void setAll(boolean isall) {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        boolean shouldall = false;
        for (Map.Entry<Integer, Boolean> entry : entries) {
            Boolean value = entry.getValue();
            if (!value) {
                shouldall = isall;
                break;
            }
        }
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(shouldall);
        }
        notifyDataSetChanged();
    }
}
