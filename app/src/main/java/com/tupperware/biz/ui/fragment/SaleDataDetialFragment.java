package com.tupperware.biz.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.saleenter.SaleReportBean;
import com.tupperware.biz.http.ProductDataManager;
import com.tupperware.biz.ui.component.DaggerSaleDataDetialFragmentComponent;
import com.tupperware.biz.ui.contract.SaleDataDetialContract;
import com.tupperware.biz.ui.module.SaleDataDetialPresenterModule;
import com.tupperware.biz.ui.presenter.SaleDataDetialPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.view.HorizChart;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/6/11.
 */

public class SaleDataDetialFragment extends BaseFragment implements SaleDataDetialContract.View {

//    @BindView(R.id.mHorizontalBarChart)
//    HorizontalBarChart mHorizontalBarChart;
    @BindView(R.id.data)
    TextView mDateText;
    @BindView(R.id.mHoriBar)
    HorizChart mHBar;
    @BindView(R.id.empty_text)
    TextView mEmptyText;
    private View rootView;

    private int mTabPosition;   //0：月，1：季，2：年
    private float spaceForBar = 10f;
    private float spaceForBarbycount;

    @Inject
    SaleDataDetialPresenter mPresenter;

    public static SaleDataDetialFragment newInstance(Bundle bundle) {
        SaleDataDetialFragment fragment = new SaleDataDetialFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
//        requestData();
        return rootView;
    }

    @Override
    public void initLayout() {
        //初始化时间
        if (mTabPosition == 1) {
            mDateText.setText(DateFormatter.getCurrentSeason());
        } else if (mTabPosition == 2) {
            mDateText.setText(DateFormatter.getCurrentYear());
        } else {
            mDateText.setText(DateFormatter.getCurrentMonth());
        }
        DaggerSaleDataDetialFragmentComponent.builder()
                .appComponent(getAppComponent())
                .saleDataDetialPresenterModule(new SaleDataDetialPresenterModule(this, ProductDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    @Override
    public void requestData() {
        showDialog();
//        initPieChart();
        mPresenter.getSaleReportData(storeCode, mTabPosition);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_sale_detial;
    }

/*    private void initPieChart() {
        // 水平条形图初始化
        ViewGroup.LayoutParams lp = mHorizontalBarChart.getLayoutParams();
        lp.width = ScreenUtil.getWidth(mContext);
        lp.height = ScreenUtil.getWidth(mContext);
        mHorizontalBarChart.setLayoutParams(lp);

        //设置相关属性
//        mHorizontalBarChart.setOnChartValueSelectedListener(this);
        mHorizontalBarChart.setTouchEnabled(false);
        mHorizontalBarChart.setDrawBarShadow(false);
        mHorizontalBarChart.setDrawValueAboveBar(true);
        mHorizontalBarChart.getDescription().setEnabled(false);
//        mHorizontalBarChart.setMaxVisibleValueCount(100);
        mHorizontalBarChart.setPinchZoom(false);
        mHorizontalBarChart.setNoDataText("无数据");
        mHorizontalBarChart.setDrawGridBackground(false);
        mHorizontalBarChart.setXAxisRenderer(new CustomXAxisRenderer(mHorizontalBarChart.getViewPortHandler(), mHorizontalBarChart.getXAxis(), mHorizontalBarChart.getTransformer(YAxis.AxisDependency.LEFT)));

        //x轴
        XAxis xAxis = mHorizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum(100f);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(10f);

        //y轴
        YAxis yAxis = mHorizontalBarChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);
//        yAxis.setAxisMaximum(60f);
        yAxis.setAxisMinimum(0f);
        yAxis.setEnabled(false);
        //y轴
        YAxis yr = mHorizontalBarChart.getAxisRight();
        yr.setEnabled(false);

        mHorizontalBarChart.setFitBars(true);
        mHorizontalBarChart.animateY(2500);
        Legend legend = mHorizontalBarChart.getLegend();
        legend.setEnabled(false);

        //设置数据
//        setData(10);
    }*/


    @Override
    public void setSaleReportData(SaleReportBean saleReportBean) {
        if (saleReportBean.getModels() == null || saleReportBean.getModels().size() == 0) {
            mHBar.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.VISIBLE);
//            mHorizontalBarChart.setNoDataText("无数据");
        } else {
            if(mEmptyText.getVisibility() == View.VISIBLE) {
                mEmptyText.setVisibility(View.GONE);
            }
            if(mHBar.getVisibility() == View.GONE) {
                mHBar.setVisibility(View.VISIBLE);
            }
            mHBar.setBarList(saleReportBean.getModels());
//            setData(saleReportBean);
        }
    }

    /*private void setData(final SaleReportBean saleReportBean) {
        final int count = saleReportBean.getModels().size();
        XAxis xAxis = mHorizontalBarChart.getXAxis();
        xAxis.setLabelCount(10);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                float spacebyCount = getspaceForBarbycount(count);
                int i = (count - 1) - (int) (value / spaceForBar);//逆序排列
                if (i >= 0 && i < count) {
                    try {
                        String name = saleReportBean.getModels().get(i).getProductName();
                        return StringUtils.insertNewLine(name);
                    } catch (UnsupportedEncodingException exception) {
                        return "";
                    }
//                    return saleReportBean.getModels().get(i).getProductName();
                } else {
                    return "";
                }
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        float barWidth = 8f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < count; i++) {
            float val = saleReportBean.getModels().get(i).getSaleNum();
            yVals1.add(new BarEntry((count - 1 - i) * spaceForBar, val));
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
    }*/

    private float getspaceForBarbycount(int count) {
        if (count < 3) {
            spaceForBarbycount = spaceForBar * 5;
        } else if (count < 5) {
            spaceForBarbycount = spaceForBar * 3;
        } else if (count < 7) {
            spaceForBarbycount = spaceForBar * 2;
        } else {
            spaceForBarbycount = spaceForBar;
        }
        return spaceForBarbycount;
    }

}