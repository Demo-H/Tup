package com.tupperware.biz.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.dhunter.common.MpAndroidChart.MvPieChart;
import com.android.dhunter.common.utils.ScreenUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/4/10.
 * 单店会员消费占比
 * 饼状图
 */

public class DataSingleVipPrecentFragment extends BaseFragment implements OnChartValueSelectedListener {

    @BindView(R.id.month)
    TextView mSelectMonth;
    @BindView(R.id.mPieChart)
    MvPieChart mPieChart;

    private int mTabPosition;
    private String mTitleFlag;

    public static DataSingleVipPrecentFragment newInstance(Bundle bundle) {
        DataSingleVipPrecentFragment fragment = new DataSingleVipPrecentFragment();
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
        // 饼状图初始化
        ViewGroup.LayoutParams lp = mPieChart.getLayoutParams();
        lp.width = ScreenUtil.getWidth(mContext);
        lp.height = ScreenUtil.getWidth(mContext);
        mPieChart.setLayoutParams(lp);

        mPieChart.setUsePercentValues(true);
        mPieChart.setBackgroundColor(Color.WHITE);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(30, 30, 30, 30);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setDrawCenterText(false);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        //变化监听
        mPieChart.setOnChartValueSelectedListener(this);

        //模拟数据

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        if(Constant.SINGLE_VIP_COUNT_PRECENT.equals(mTitleFlag) || Constant.STORE_VIP_SALE_PRECENT.equals(mTitleFlag)) {
            entries.add(new PieEntry(new Random().nextInt(100), "普通会员"));
            entries.add(new PieEntry(new Random().nextInt(100), "高级会员"));
            entries.add(new PieEntry(new Random().nextInt(100), "粉丝"));
            entries.add(new PieEntry(new Random().nextInt(100), "自然入店"));
        } else if(Constant.SINGLE_SALE_ATTRIBUTE.equals(mTitleFlag) || Constant.STORE_SALE_ATTRIBUTE.equals(mTitleFlag)) {
            entries.add(new PieEntry(45, "线上销售"));
            entries.add(new PieEntry(28, "线下销售"));
            entries.add(new PieEntry(15, "团体销售"));
            entries.add(new PieEntry(5, "私人订制"));
            entries.add(new PieEntry(3, "理家课堂"));
            entries.add(new PieEntry(3, "其他"));
        }

        //设置数据
        setData(entries);

        mPieChart.animateX(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setEnabled(false);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.BLACK);
        mPieChart.setEntryLabelTextSize(12f);
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_single_vip_precent;
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "SingleVipPrecent");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setValueLinePart1Length(0.7f);
        dataSet.setValueLinePart2Length(0.9f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.MATERIAL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColors(colors);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
