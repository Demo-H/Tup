package com.tupperware.biz.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.dhunter.common.widget.mzBannerView.holder.MZViewHolder;
import com.tupperware.biz.R;

/**
 * Created by dhunter on 2018/3/14.
 */

public class DataWindowViewHolder implements MZViewHolder<Integer> {

    private TextView mActualSaleCount; // actual_sale_count
    private TextView mTargetCount;  // target_count
    private TextView mMarginCount;   //margin_count

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_data_window_select_content,null);
        mActualSaleCount = (TextView) view.findViewById(R.id.actual_sale_count);
        mTargetCount = (TextView) view.findViewById(R.id.target_count);
        mMarginCount = (TextView) view.findViewById(R.id.margin_count);
        return view;
    }

    @Override
    public void onBind(Context context, int position, Integer data) {

    }
}
