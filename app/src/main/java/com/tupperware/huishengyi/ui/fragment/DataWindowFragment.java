package com.tupperware.huishengyi.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.dhunter.common.widget.tablayout.SegmentTabLayout;
import com.android.dhunter.common.widget.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.DataWindowAdapter;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.ui.DataWindowChartActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/2/2.
 * 需求变更,保留该页面，数据窗使用DataShowFragment
 */

public class DataWindowFragment extends BaseFragment {

    View rootView;
    @BindView(R.id.data_window_title)
    TextView mTitleView;
    @BindView(R.id.mTargetPieChart)
    PieChart mPieChart;
    @BindView(R.id.finished_num)
    TextView mFinishNum;
    @BindView(R.id.date_tab)
    SegmentTabLayout mTab;
    @BindView(R.id.viewpage)
    ViewPager mViewPager;
//    @BindView(R.id.data_window_banner)
//    MZBannerView  mzBannerView;

    @BindView(R.id.single_vip_count_precent)
    Button mSingleVipCountPrecent;
    @BindView(R.id.single_vip_status)
    Button mSingleVipStatus;
    @BindView(R.id.single_product_top10)
    Button mSingleProductTop10;
    @BindView(R.id.single_product_sale_top10)
    Button mSingleProductSaleTop10;
    @BindView(R.id.single_sale_attribute)
    Button mSingleSaleAttribute;
    @BindView(R.id.single_main_product)
    Button mSingleMainProduct;

    @BindView(R.id.store_sale_money_list)
    Button mStoreSaleMoneyList;
    @BindView(R.id.store_sale_list)
    Button mStoreSaleList;
    @BindView(R.id.store_sale_analysis)
    Button mStoreSaleAnalysis;
    @BindView(R.id.store_vip_sale_precent)
    Button mStoreVipSalePrecent;
    @BindView(R.id.store_vip_status_analysis)
    Button mStoreVipStatusAnalysis;
    @BindView(R.id.store_sale_attribute)
    Button mStoreSaleAttribute;
    @BindView(R.id.product_sale_top10)
    Button mProductSaleTop10;
    @BindView(R.id.product_sale_money_top10)
    Button mProductSaleMoneyTop10;
    @BindView(R.id.main_product_list)
    Button mMainProductList;


    private DataWindowAdapter mAdapter;


    public static DataWindowFragment newInstance() {
        DataWindowFragment fragment = new DataWindowFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initLayout();
        initLayoutData();
        return rootView;
    }

    @Override
    public void initLayout() {
        initPieChart();
        initTimeTab();
//        initBanner();
    }

    @Override
    public void initLayoutData() {
        mPieChart.setData(getPieData(70, 30, rootView.getResources().getColor(R.color.white)));
        mPieChart.highlightValues(null);
        mPieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        mPieChart.invalidate();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_window;
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
        colors.add(0xffff7000);
        dataSet.setColors(colors);

        //设置数据开始画
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        return data;
    }

    private void initPieChart() {
        mPieChart.setTouchEnabled(false);
        mPieChart.setDrawHoleEnabled(true); //显示中间的洞
//        mPieChart.setBackgroundColor(getResources().getColor(R.color.color_ff7000));
        mPieChart.setHoleColor(0xffff7000); //洞的颜色
        mPieChart.setTransparentCircleColor(R.color.color_ff7000);
        mPieChart.setDrawSliceText(false);//不显示切片里面的字体，就是pie 块里面的字体
        mPieChart.setDescription(null); //不显示描述
        mPieChart.setHoleRadius(80f); //洞的大小
        mPieChart.setTransparentCircleRadius(20f); //效果的大小
        mPieChart.setDrawCenterText(false);//中心的文字也不要写了
        mPieChart.setRotationAngle(108); //显示的角度 90+ X% * 360
        Legend l = mPieChart.getLegend();
        l.setEnabled(false);//对pie 块的描述也不要显示
    }

    private void initTimeTab() {
        mAdapter = new DataWindowAdapter(getActivity(), getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTab.setTabData(getResources().getStringArray(R.array.month_q_year));
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTab.setCurrentTab(0);
    }

 /*   private void initBanner() {
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<2; i++){
            list.add(RES[i]);
        }
        mzBannerView.setIndicatorVisible(true);
        mzBannerView.setIndicatorRes(R.drawable.indicator_normal_item,R.drawable.indicator_selected_item);
        mzBannerView.setPages(list, new MZHolderCreator<DataWindowViewHolder>() {
            @Override
            public DataWindowViewHolder createViewHolder() {
                return new DataWindowViewHolder();
            }
        });
    }*/

    @OnClick({R.id.single_vip_count_precent, R.id.single_vip_status, R.id.single_product_top10, R.id.single_product_sale_top10,
            R.id.single_sale_attribute, R.id.single_main_product, R.id.store_sale_money_list, R.id.store_sale_list,
            R.id.store_sale_analysis, R.id.store_vip_sale_precent, R.id.store_vip_status_analysis, R.id.store_sale_attribute,
            R.id.product_sale_top10, R.id.product_sale_money_top10, R.id.main_product_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single_vip_count_precent:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.SINGLE_VIP_COUNT_PRECENT);
                break;
            case R.id.single_vip_status:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.SINGLE_VIP_STATUS);
                break;
            case R.id.single_product_top10:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.SINGLE_PRODUCT_TOP);
                break;
            case R.id.single_product_sale_top10:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.SINGLE_PRODUCT_SALE_TOP);
                break;
            case R.id.single_sale_attribute:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.SINGLE_SALE_ATTRIBUTE);
                break;
            case R.id.single_main_product:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.SINGLE_MAIN_PRODUCT);
                break;
            case R.id.store_sale_money_list:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.STORE_SALE_MONEY_LIST);
                break;
            case R.id.store_sale_list:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.STORE_SALE_LIST);
                break;
            case R.id.store_sale_analysis:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.STORE_SALE_ANALYSIS);
                break;
            case R.id.store_vip_sale_precent:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.STORE_VIP_SALE_PRECENT);
                break;
            case R.id.store_vip_status_analysis:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.STORE_VIP_STATUS_ANALYSIS);
                break;
            case R.id.store_sale_attribute:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.STORE_SALE_ATTRIBUTE);
                break;
            case R.id.product_sale_top10:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.PRODUCT_SALE_TOP);
                break;
            case R.id.product_sale_money_top10:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.PRODUCT_SALE_MONEY_TOP);
                break;
            case R.id.main_product_list:
                jumptoActivity(getActivity(), DataWindowChartActivity.class, Constant.MAIN_PRODUCT_LIST);
             break;
        }
    }

    private void jumptoActivity(Context context, Class<?> _cls, String flag) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ACTIVITY_TITLE, flag);
        context.startActivity(intent);
    }

}
