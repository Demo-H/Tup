package com.tupperware.huishengyi.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.dhunter.common.utils.ScreenUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/11.
 * 竖直条形图
 */

public class DataStoreSaleMoneyFragment extends BaseFragment {

    @BindView(R.id.month)
    TextView mSelectMonth;
    @BindView(R.id.mBarChart)
    BarChart mBarChart;

    private int mTabPosition;
    private String mTitleFlag;

    public static DataStoreSaleMoneyFragment newInstance(Bundle bundle) {
        DataStoreSaleMoneyFragment fragment = new DataStoreSaleMoneyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
            mTitleFlag = bundle.getString(Constant.ACTIVITY_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
        initLayoutData();
        return view;
    }

    @Override
    public void initLayout() {
        // 条形图初始化
        ViewGroup.LayoutParams lp = mBarChart.getLayoutParams();
        lp.width = ScreenUtil.getWidth(mContext);
        lp.height = ScreenUtil.getWidth(mContext);
        mBarChart.setLayoutParams(lp);

        //设置表格上的点，被点击的时候，的回调函数
//        mBarChart.setOnChartValueSelectedListener(this);
        mBarChart.setDrawBarShadow(false);
        //设置所有的数值在图形的上面,而不是图形上
        mBarChart.setDrawValueAboveBar(true);
        //描述设置显示在y轴上的单位
        mBarChart.getDescription().setEnabled(true);
        mBarChart.getDescription().setText("(万元)");
        mBarChart.getDescription().setPosition(65,60);
        mBarChart.getDescription().setTextColor(Color.BLACK);

        // 如果60多个条目显示在图表,drawn没有值
//        mBarChart.setMaxVisibleValueCount(60);
        // 扩展现在只能分别在x轴和y轴
        mBarChart.setPinchZoom(false);
        //是否显示表格颜色
        mBarChart.setDrawGridBackground(false);
        mBarChart.setNoDataText("暂无数据");
        mBarChart.setHighlightFullBarEnabled(false);

        mBarChart.animateY(2500);


        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        // 1个月的时间间隔
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);
        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(12f);

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        //这个替换setStartAtZero(true)
        leftAxis.setAxisMinimum(0f);
//        leftAxis.setAxisMaximum(600f);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return null;
//            }
//
//            @Override
//            public int getDecimalDigits() {
//                return 0;
//            }
//        });

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setEnabled(false);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f);
//        rightAxis.setAxisMaximum(50f);

        // 设置标示，就是那个一组y的value的
        Legend l = mBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        //样式
        l.setForm(Legend.LegendForm.SQUARE);
        //字体
        l.setFormSize(9f);
        //大小
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        //模拟数据
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(1, 200));
        yVals1.add(new BarEntry(2, 300));
        yVals1.add(new BarEntry(3, 400));
        yVals1.add(new BarEntry(4, 530));
        yVals1.add(new BarEntry(5, 100));
        yVals1.add(new BarEntry(6, 200));
        yVals1.add(new BarEntry(7, 300));
        yVals1.add(new BarEntry(8, 400));
        yVals1.add(new BarEntry(9, 400));
        yVals1.add(new BarEntry(10, 440));
        yVals1.add(new BarEntry(11, 400));
        yVals1.add(new BarEntry(12, 405));
        setData(yVals1);

    }

    //设置数据
    private void setData(ArrayList yVals1) {
        float start = 1f;
        BarDataSet set1;
        if (mBarChart.getData() != null &&
                mBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "销售量");
            //设置有四种颜色
            set1.setColors(0xffff7700);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            //设置数据
            mBarChart.setData(data);
        }
    }

    @Override
    public void initLayoutData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_store_sale_money;
    }
}
