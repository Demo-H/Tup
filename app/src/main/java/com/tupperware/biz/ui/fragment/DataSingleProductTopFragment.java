package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.dhunter.common.utils.ScreenUtil;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/10.
 * 水平条形图
 */

public class DataSingleProductTopFragment extends BaseFragment implements OnChartValueSelectedListener {

    @BindView(R.id.month)
    TextView mSelectMonth;
    @BindView(R.id.mHorizontalBarChart)
    HorizontalBarChart mHorizontalBarChart;

    private int mTabPosition;
    private String mTitleFlag;
    private float spaceForBar = 10f;
    private final String lable[]={"TPW-C1型家居净水系统","都市暖心保温瓶4件套","9.4升果菜保鲜盒2件套","纤巧超值套装",
            "精钢系列道具组合6件套","美满人生锅具套装","MM长方储藏组合2件套","MM迷你长方储藏盒4件套","私人订制43件套","滤芯矿物增强棒大礼包"};


    public static DataSingleProductTopFragment newInstance(Bundle bundle) {
        DataSingleProductTopFragment fragment = new DataSingleProductTopFragment();
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
//        requestData();
        return view;
    }

    @Override
    public void initLayout() {
        // 水平条形图初始化
        ViewGroup.LayoutParams lp = mHorizontalBarChart.getLayoutParams();
        lp.width = ScreenUtil.getWidth(mContext);
        lp.height = ScreenUtil.getWidth(mContext);
        mHorizontalBarChart.setLayoutParams(lp);

        //设置相关属性
        mHorizontalBarChart.setOnChartValueSelectedListener(this);
        mHorizontalBarChart.setDrawBarShadow(false);
        mHorizontalBarChart.setDrawValueAboveBar(true);
        mHorizontalBarChart.getDescription().setEnabled(false);
//        mHorizontalBarChart.setMaxVisibleValueCount(100);
        mHorizontalBarChart.setPinchZoom(false);
        mHorizontalBarChart.setNoDataText("无数据");
        mHorizontalBarChart.setDrawGridBackground(false);

         //x轴
        XAxis xAxis = mHorizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);
//        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(9f);
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(10f);
        xAxis.setLabelCount(10);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return lable[(int) (value / spaceForBar)];
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });


        //y轴
        YAxis yAxis = mHorizontalBarChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
        yAxis.setAxisMaximum(120f);
        yAxis.setEnabled(false);

        //y轴
        YAxis yr = mHorizontalBarChart.getAxisRight();
        yr.setEnabled(false);

        //设置数据
        setData(10, 50);
        mHorizontalBarChart.setFitBars(true);
        mHorizontalBarChart.animateY(2500);

        Legend legend = mHorizontalBarChart.getLegend();
        legend.setEnabled(false);

    }

    //来点随机数吧
    private void setData(int count, float range) {
        float barWidth = 8f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < count; i++) {
            float val = i * spaceForBar + 10f;
            yVals1.add(new BarEntry(i * spaceForBar, val));
        }
        BarDataSet set1;
        if (mHorizontalBarChart.getData() != null &&
                mHorizontalBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mHorizontalBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mHorizontalBarChart.getData().notifyDataChanged();
            mHorizontalBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet 1");
            set1.setColor(0xffff7700);
            set1.setDrawValues(true);
            //显示为整数
            set1.setValueFormatter(new IValueFormatter() {

                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    int valuedate = (int) value;
                    return valuedate + "";
                }
            });
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(barWidth);
            mHorizontalBarChart.setData(data);
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_single_product_top;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public class MyValueFormatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return StringUtils.double2String(value, 2);
        }
    }
}
