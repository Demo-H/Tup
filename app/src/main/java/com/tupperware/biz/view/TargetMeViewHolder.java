package com.tupperware.biz.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.dhunter.common.widget.mzBannerView.holder.MZViewHolder;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.tupperware.biz.R;

import java.util.ArrayList;

/**
 * Created by dhunter on 2018/3/13.
 */

public class TargetMeViewHolder implements MZViewHolder<Integer> {

    private TextView mFinishCount;
    private TextView mMarginCount;
    private PieChart mPieChart;
    private TextView mFinishNum;
    private TextView mTargetCount;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.target_me_banner_content,null);
        mFinishCount = (TextView) view.findViewById(R.id.finish_count);
        mMarginCount = (TextView) view.findViewById(R.id.target_margin_count);
        mFinishNum = (TextView) view.findViewById(R.id.finished_num);
        mTargetCount = (TextView) view.findViewById(R.id.target_count);

        mPieChart = (PieChart) view.findViewById(R.id.mTargetPieChart);
        mPieChart.setTouchEnabled(false);
//        mPieChart.setCenterTextTypeface(tf); //设置字体
        mPieChart.setDrawHoleEnabled(true); //显示中间的洞
        mPieChart.setTransparentCircleColor(Color.WHITE);//洞的颜色
        mPieChart.setDrawSliceText(false);//不显示切片里面的字体，就是pie 块里面的字体
        mPieChart.setDescription(null); //不显示描述

        mPieChart.setHoleRadius(70f); //洞的大小
        mPieChart.setTransparentCircleRadius(70f); //效果的大小
        mPieChart.setDrawCenterText(false);//中心的文字也不要写了
        mPieChart.setRotationAngle(108); //显示的角度 90+ X% * 360

        Legend l = mPieChart.getLegend();
        l.setEnabled(false);//对pie 块的描述也不要显示

        return view;
    }

    @Override
    public void onBind(Context context, int position, Integer data) {
        mPieChart.setData(getPieData(725, 347, context.getResources().getColor(R.color.color_ff7000)));
        mPieChart.highlightValues(null);
        mPieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        mPieChart.invalidate();

    }

    /**
     * 组合pie chart 的数据
     *
     * @param completeNum   完成的
     * @param remainNum     剩下的
     * @param completeColor 颜色
     * @return
     */
    private PieData getPieData(int completeNum, int remainNum, int completeColor) {
        //X轴数据
        ArrayList<String> xValues = new ArrayList<String>();
        xValues.add("1");
        xValues.add("2");
        xValues.add("3");

        //Y轴数据 -- start ************************************* //
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        yValues.add(new PieEntry(completeNum, 0));
        yValues.add(new PieEntry(remainNum, 1));
        yValues.add(new PieEntry((completeNum + remainNum) / 9f, 2));
        PieDataSet dataSet = new PieDataSet(yValues, "Election Results");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);
        dataSet.setDrawValues(false);
        //Y轴数据 -- end ************************************* //

        //颜色值
        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(completeColor);
        colors.add(0xffe9e9e9);
        colors.add(0xffffffff);
        dataSet.setColors(colors);

        //设置数据开始画
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        return data;
    }
}
