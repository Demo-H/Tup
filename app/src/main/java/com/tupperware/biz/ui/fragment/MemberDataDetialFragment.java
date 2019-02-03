package com.tupperware.biz.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.MemberAddBean;
import com.tupperware.biz.entity.member.MemberReportBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhunter on 2018/6/11.
 */

public class MemberDataDetialFragment extends BaseFragment {

    @BindView(R.id.mTargetPieChart)
    PieChart mPieChart;
    @BindView(R.id.add_new_title)
    TextView mAddNewTitle;
    @BindView(R.id.finished_zhaomu)
    TextView mZhaomuFinished;
    @BindView(R.id.today_add_member)
    TextView mTodayAdd;
    @BindView(R.id.month_target_zhaomu)
    TextView mMonthTargetZhaomu;
    @BindView(R.id.month_target_text)
    TextView mMonthTargetZhaomuStatus;
    @BindView(R.id.month_target_text_standard)
    TextView mMonthTargetZhaomuStatusStandard;
//    @BindView(R.id.month_target_icon)
//    SimpleDraweeView mMonthTargetZhaomuIcon;
    @BindView(R.id.season_target_text)
    TextView mSeasonTargetZhaomuStatus;
    @BindView(R.id.season_target_text_standard)
    TextView mSeasonTargetZhaomuStatusStandard;
//    @BindView(R.id.season_target_icon)
//    SimpleDraweeView mSeasonTargetZhaomuIcon;
    @BindView(R.id.year_target_text)
    TextView mYearTargetZhaomuStatus;
    @BindView(R.id.year_target_text_standard)
    TextView mYearTargetZhaomuStatusStandard;
//    @BindView(R.id.year_target_icon)
//    SimpleDraweeView mYearTargetZhaomuIcon;

    private int mTabPosition;
    private MemberReportBean memberReportBean;
    private MemberAddBean memberAddBean;
    private View rootview;

    public static MemberDataDetialFragment newInstance(Bundle bundle) {
        MemberDataDetialFragment fragment = new MemberDataDetialFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTabPosition = bundle.getInt(Constant.FRAGMENT_TAB_POSITION);
            memberReportBean = (MemberReportBean) bundle.getSerializable(Constant.MEMBER_REPORT_DATA);
            memberAddBean = (MemberAddBean) bundle.getSerializable(Constant.MEMBER_ADD_NEW_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootview);
        initLayout();
//        requestData();
        return rootview;
    }

    @Override
    public void initLayout() {
        initPieChart();
       /* if (mTabPosition == 0) {
            mAddNewTitle.setText(getResources().getString(R.string.today_add_fans));
        } else */if (mTabPosition == 0) {
            mAddNewTitle.setText(getResources().getString(R.string.today_add_member));
        } else {
            mAddNewTitle.setText(getResources().getString(R.string.today_add_vip_member));
        }
        if (memberReportBean != null) {
            refreshBaseData(memberReportBean);
        }
        if (memberAddBean != null) {
            refreshAddData(memberAddBean);
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_data_member_detial;
    }

    private void initPieChart() {
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
        onBindDate(0,60); //初始化数据随意指定
    }

    /**
     *
     * @param completeNum   完成的
     * @param remainNum     剩下的
     *  对于参数为0和1的时候，数据显示需要单独处理
     */
    private void onBindDate(int completeNum, int remainNum) {
        if(completeNum == 0) {
            mPieChart.setData(getPieData(completeNum, 60, getActivity().getResources().getColor(R.color.color_ff7000)));
        } else if(remainNum <= 0) {
            mPieChart.setData(getPieData(60, 0, getActivity().getResources().getColor(R.color.color_ff7000)));
        } else if(completeNum == 1 || remainNum == 1) {
            mPieChart.setData(getPieData(completeNum * 2, remainNum * 2, getActivity().getResources().getColor(R.color.color_ff7000)));
        }else {
            mPieChart.setData(getPieData(completeNum, remainNum, getActivity().getResources().getColor(R.color.color_ff7000)));
        }
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
        if(remainNum == 0) {
            xValues.add("1");
            xValues.add("2");
        } else {
            xValues.add("1");
            xValues.add("2");
            xValues.add("3");
        }

        //Y轴数据 -- start ************************************* //
        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        if(remainNum == 0) {
            yValues.add(new PieEntry(completeNum, 0));
            yValues.add(new PieEntry((completeNum + remainNum) / 9f, 1));
        } else {
            yValues.add(new PieEntry(completeNum, 0));
            yValues.add(new PieEntry(remainNum, 1));
            yValues.add(new PieEntry((completeNum + remainNum) / 9f, 2));
        }
        PieDataSet dataSet = new PieDataSet(yValues, "Election Results");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);
        dataSet.setDrawValues(false);
        //Y轴数据 -- end ************************************* //

        //颜色值
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if(remainNum == 0) {
            colors.add(completeColor);
            colors.add(0xffffffff);
        } else {
            colors.add(completeColor);
            colors.add(0xffe9e9e9);
            colors.add(0xffffffff);
        }
        dataSet.setColors(colors);

        //设置数据开始画
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        return data;
    }

    public void refreshBaseData(MemberReportBean memberReportBean) {
        if(mZhaomuFinished == null || mMonthTargetZhaomu == null || mMonthTargetZhaomuStatus == null
                || mYearTargetZhaomuStatus == null /*|| mMonthTargetZhaomuIcon == null || mSeasonTargetZhaomuIcon == null
                || mYearTargetZhaomuIcon == null*/) {
            return;
        }
        switch (mTabPosition) {
//            case 0:
//                refreshFans(memberReportBean);
//                break;
            case 0:
                refreshCommonMember(memberReportBean);
                break;
            case 1:
                refreshVipMember(memberReportBean);
                break;
        }

    }

    private void refreshFans(MemberReportBean memberReportBean) {
        int monthFansCount = memberReportBean.getModel().getFansStatisticsVO().getMonthFansCount();
        int monthFansTargetCount = memberReportBean.getModel().getFansStatisticsVO().getMonthFansTargetCount();
        int quarterFansCount = memberReportBean.getModel().getFansStatisticsVO().getQuarterFansCount();
        int quarterFansTargetCount = memberReportBean.getModel().getFansStatisticsVO().getQuarterFansTargetCount();
        int yearFansCount = memberReportBean.getModel().getFansStatisticsVO().getYearFansCount();
        int yearFansTargetCount = memberReportBean.getModel().getFansStatisticsVO().getYearFansTargetCount();
        mZhaomuFinished.setText(monthFansCount + "");
        mMonthTargetZhaomu.setText(monthFansTargetCount + "");
        mMonthTargetZhaomuStatus.setText(monthFansCount + "");
        mSeasonTargetZhaomuStatus.setText(quarterFansCount + "");
        mYearTargetZhaomuStatus.setText(yearFansCount + "");
        mMonthTargetZhaomuStatusStandard.setText("个/" + monthFansTargetCount + "个");
        mSeasonTargetZhaomuStatusStandard.setText("个/" + quarterFansTargetCount + "个");
        mYearTargetZhaomuStatusStandard.setText("个/" + yearFansTargetCount + "个");
//        if(monthFansCount < monthFansTargetCount) {
//            mMonthTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_9b9b9b));
//            mMonthTargetZhaomuIcon.setImageResource(R.mipmap.data_undone_ic);
//        } else {
//            mMonthTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_ff7000));
//            mMonthTargetZhaomuIcon.setImageResource(R.mipmap.data_done_ic);
//        }
//        if(quarterFansCount < quarterFansTargetCount) {
//            mSeasonTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_9b9b9b));
//            mSeasonTargetZhaomuIcon.setImageResource(R.mipmap.data_undone_ic);
//        } else {
//            mSeasonTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_ff7000));
//            mSeasonTargetZhaomuIcon.setImageResource(R.mipmap.data_done_ic);
//        }
//        if(yearFansCount < yearFansTargetCount)  {
//            mYearTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_9b9b9b));
//            mYearTargetZhaomuIcon.setImageResource(R.mipmap.data_undone_ic);
//        } else {
//            mYearTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_ff7000));
//            mYearTargetZhaomuIcon.setImageResource(R.mipmap.data_done_ic);
//        }
        onBindDate(monthFansCount, monthFansTargetCount - monthFansCount);
    }

    private void refreshCommonMember(MemberReportBean memberReportBean) {
        int monthCommonMemberCount = memberReportBean.getModel().getCommonMemberStatisticsVO().getMonthCommonMemberCount();
        int monthCommonMemberTargetCount = memberReportBean.getModel().getCommonMemberStatisticsVO().getMonthCommonMemberTargetCount();
        int quarterCommonMemberCount = memberReportBean.getModel().getCommonMemberStatisticsVO().getQuarterCommonMemberCount();
        int quarterCommonMemberTargetCount = memberReportBean.getModel().getCommonMemberStatisticsVO().getQuarterCommonMemberTargetCount();
        int yearCommonMemberCount = memberReportBean.getModel().getCommonMemberStatisticsVO().getYearCommonMemberCount();
        int yearCommonMemberTargetCount = memberReportBean.getModel().getCommonMemberStatisticsVO().getYearCommonMemberTargetCount();
        mZhaomuFinished.setText(monthCommonMemberCount + "");
        mMonthTargetZhaomu.setText(monthCommonMemberTargetCount + "");
        mMonthTargetZhaomuStatus.setText(monthCommonMemberCount + "");
        mSeasonTargetZhaomuStatus.setText(quarterCommonMemberCount + "");
        mYearTargetZhaomuStatus.setText(yearCommonMemberCount + "");
        mMonthTargetZhaomuStatusStandard.setText("个/" + monthCommonMemberTargetCount + "个");
        mSeasonTargetZhaomuStatusStandard.setText("个/" + quarterCommonMemberTargetCount + "个");
        mYearTargetZhaomuStatusStandard.setText("个/" + yearCommonMemberTargetCount + "个");
//        if(monthCommonMemberCount < monthCommonMemberTargetCount) {
//            mMonthTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_9b9b9b));
//            mMonthTargetZhaomuIcon.setImageResource(R.mipmap.data_undone_ic);
//        } else {
//            mMonthTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_ff7000));
//            mMonthTargetZhaomuIcon.setImageResource(R.mipmap.data_done_ic);
//        }
//        if(quarterCommonMemberCount < quarterCommonMemberTargetCount) {
//            mSeasonTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_9b9b9b));
//            mSeasonTargetZhaomuIcon.setImageResource(R.mipmap.data_undone_ic);
//        } else {
//            mSeasonTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_ff7000));
//            mSeasonTargetZhaomuIcon.setImageResource(R.mipmap.data_done_ic);
//        }
//        if(yearCommonMemberCount < yearCommonMemberTargetCount)  {
//            mYearTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_9b9b9b));
//            mYearTargetZhaomuIcon.setImageResource(R.mipmap.data_undone_ic);
//        } else {
//            mYearTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_ff7000));
//            mYearTargetZhaomuIcon.setImageResource(R.mipmap.data_done_ic);
//        }
        onBindDate(monthCommonMemberCount, monthCommonMemberTargetCount - monthCommonMemberCount );
    }

    private void refreshVipMember(MemberReportBean memberReportBean) {
        int monthVipMemberCount = memberReportBean.getModel().getVipMemberStatisticsVO().getMonthVipMemberCount();
        int monthVipMemberTargetCount = memberReportBean.getModel().getVipMemberStatisticsVO().getMonthVipMemberTargetCount();
        int quarterVipMemberCount = memberReportBean.getModel().getVipMemberStatisticsVO().getQuarterVipMemberCount();
        int quarterVipMemberTargetCount = memberReportBean.getModel().getVipMemberStatisticsVO().getQuarterVipMemberTargetCount();
        int yearVipMemberCount = memberReportBean.getModel().getVipMemberStatisticsVO().getYearVipMemberCount();
        int yearVipMemberTargetCount = memberReportBean.getModel().getVipMemberStatisticsVO().getYearVipMemberTargetCount();
        mZhaomuFinished.setText(monthVipMemberCount + "");
        mMonthTargetZhaomu.setText(monthVipMemberTargetCount + "");
        mMonthTargetZhaomuStatus.setText(monthVipMemberCount + "");
        mSeasonTargetZhaomuStatus.setText(quarterVipMemberCount + "");
        mYearTargetZhaomuStatus.setText(yearVipMemberCount + "");
        mMonthTargetZhaomuStatusStandard.setText("个/" + monthVipMemberTargetCount + "个");
        mSeasonTargetZhaomuStatusStandard.setText("个/" + quarterVipMemberTargetCount + "个");
        mYearTargetZhaomuStatusStandard.setText("个/" + yearVipMemberTargetCount + "个");
//        if(monthVipMemberCount < monthVipMemberTargetCount) {
//            mMonthTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_9b9b9b));
//            mMonthTargetZhaomuIcon.setImageResource(R.mipmap.data_undone_ic);
//        } else {
//            mMonthTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_ff7000));
//            mMonthTargetZhaomuIcon.setImageResource(R.mipmap.data_done_ic);
//        }
//        if(quarterVipMemberCount < quarterVipMemberTargetCount) {
//            mSeasonTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_9b9b9b));
//            mSeasonTargetZhaomuIcon.setImageResource(R.mipmap.data_undone_ic);
//        } else {
//            mSeasonTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_ff7000));
//            mSeasonTargetZhaomuIcon.setImageResource(R.mipmap.data_done_ic);
//        }
//        if(yearVipMemberCount < yearVipMemberTargetCount)  {
//            mYearTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_9b9b9b));
//            mYearTargetZhaomuIcon.setImageResource(R.mipmap.data_undone_ic);
//        } else {
//            mYearTargetZhaomuStatus.setTextColor(getResources().getColor(R.color.color_ff7000));
//            mYearTargetZhaomuIcon.setImageResource(R.mipmap.data_done_ic);
//        }
        onBindDate(monthVipMemberCount, monthVipMemberTargetCount - monthVipMemberCount );
    }

    public void refreshAddData(MemberAddBean memberAddBean) {
        if (mTodayAdd == null)
            return;
        /*if (mTabPosition == 0) {
            mTodayAdd.setText(memberAddBean.getModel().getAddFansCount() + "");
        } else*/ if (mTabPosition == 0) {
            mTodayAdd.setText(memberAddBean.getModel().getAddCommonMemberCount() + "");
        } else {
            mTodayAdd.setText(memberAddBean.getModel().getAddVipMemberCount() + "");
        }
    }

    public void updateArguments(int mTabPosition, MemberReportBean memberReportBean, MemberAddBean memberAddBean) {
        this.mTabPosition = mTabPosition;
        this.memberReportBean = memberReportBean;
        this.memberAddBean = memberAddBean;
        try {
            Bundle args = getArguments();
            if (args != null) {
                args.putInt(Constant.FRAGMENT_TAB_POSITION, mTabPosition);
                args.putSerializable(Constant.MEMBER_REPORT_DATA, memberReportBean);
                args.putSerializable(Constant.MEMBER_ADD_NEW_DATA, memberAddBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
