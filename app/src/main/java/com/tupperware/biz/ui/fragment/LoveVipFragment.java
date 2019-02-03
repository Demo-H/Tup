package com.tupperware.biz.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseFragment;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.ui.activities.ActionListActivity;
import com.tupperware.biz.ui.activities.MemberListActivity;
import com.tupperware.biz.ui.activities.PurposeFollowActivity;
import com.tupperware.biz.ui.activities.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/2/2.
 */

public class LoveVipFragment extends BaseFragment /*implements LoveVipContract.View, BaseQuickAdapter.RequestLoadMoreListener,
        PositionChangedListener*/ {

    @BindView(R.id.search)
//    SearchEditText mSearchEdit;
    LinearLayout mSearchEdit;
    @BindView(R.id.follow_project_rl)
    LinearLayout mFollowProjectRL;
    @BindView(R.id.action_invitate)
    LinearLayout mActionInvitate;
    @BindView(R.id.my_members)
    LinearLayout myMembers;
//    @BindView(R.id.mPieChart)
//    PieChart mPieChart;
//    @BindView(R.id.youhuiquan_deadline_rl)
//    RelativeLayout mYhqDeadline;
//    @BindView(R.id.huicoin_deadline_rl)
//    RelativeLayout mHCoinDeadline;

//    @BindView(R.id.member_zhaomu)
//    Button mZhaomuButton;
//    @BindView(R.id.fans_change)
//    Button mFansButton;
//    @BindView(R.id.member_status)
//    Button mStatusButton;
//    @BindView(R.id.member_detial)
//    Button mDetialButton;

//    private LoveVipRecycleAdapter mAdapter;
//    private RecyclerView mLoveVipRecyclerView;

//    @Inject
//    LoveVipPresenter mPresenter;

    public static LoveVipFragment newInstance() {
        LoveVipFragment fragment = new LoveVipFragment();
        return fragment;
    }

    @Nullable
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
//        initPieChat();
    }

    @Override
    public void requestData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_love_vip;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

//    private void initPieChat() {
//
//        //饼状图
//        mPieChart.setUsePercentValues(false);
//        mPieChart.setCenterText(null);
//        mPieChart.getDescription().setEnabled(false);
//        mPieChart.getLegend().setEnabled(false);
//        mPieChart.setExtraOffsets(0,0,0,0);
//        mPieChart.setDrawHoleEnabled(true);
//        mPieChart.setHoleRadius(70f); //洞的大小
//        mPieChart.setDrawEntryLabels(false);
//
//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//        entries.add(new PieEntry(145, "粉丝"));
//        entries.add(new PieEntry(89, "普通会员"));
//        entries.add(new PieEntry(65, "高级会员"));
//
//
//        mPieChart.setRotationAngle(0);
//        // 触摸旋转
//        mPieChart.setRotationEnabled(true);
//        mPieChart.setHighlightPerTapEnabled(true);
//
//        //设置数据
//        setChartData(entries);
//    }
    //设置数据
//    private void setChartData(ArrayList<PieEntry> entries) {
//        PieDataSet dataSet = new PieDataSet(entries, null);
//        dataSet.setSliceSpace(0);   //设置饼状Item之间的间隙
//        dataSet.setSelectionShift(5f);  //设置饼状Item被选中时变化的距离
//
//        //数据和颜色
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        colors.add(Color.parseColor("#ffc560"));
//        colors.add(Color.parseColor("#ffa066"));
//        colors.add(Color.parseColor("#ff7300"));
//
//        dataSet.setColors(colors);
//        PieData data = new PieData(dataSet);
//        data.setDrawValues(false);    //设置是否显示数据实体(百分比，true:以下属性才有意义)
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.WHITE);
//        mPieChart.setData(data);
//        mPieChart.highlightValues(null);
//        mPieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
//        //刷新
//        mPieChart.invalidate();
//    }

    @OnClick({ R.id.search, R.id.follow_project_rl, R.id.action_invitate, R.id.my_members})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.search:
                jumptoActivity(getActivity(), SearchActivity.class, Constant.LOVE_VIP_FRAGMENT);
                break;
            case R.id.follow_project_rl:
                jumptoActivity(getActivity(), PurposeFollowActivity.class, null);
                break;
            case R.id.action_invitate:
                jumptoActivity(getActivity(), ActionListActivity.class, Constant.LOVE_VIP_FRAGMENT);
                break;
            case R.id.my_members:
                jumptoActivity(getActivity(), MemberListActivity.class, null);
                break;
//            case R.id.youhuiquan_deadline_rl:
//                jumptoActivity(getActivity(), BenefitCoinDeadlineActivity.class, Constant.COUPON);
//                break;
//            case R.id.huicoin_deadline_rl:
//                jumptoActivity(getActivity(), BenefitCoinDeadlineActivity.class, Constant.BENEFIT);
//                break;
//            case R.id.member_zhaomu:
//                jumptoActivity(getActivity(), MemberZhaomuActivity.class, null);
//                break;
//            case R.id.fans_change:
//                jumptoActivity(getActivity(), FansChangeActivity.class, null);
//                break;
//            case R.id.member_status:
//                jumptoActivity(getActivity(), MemberStatusActivity.class, null);
//                break;
//            case R.id.member_detial:
//                jumptoActivity(getActivity(), MemberConsumeActivity.class, null);
//                break;
        }
    }

    private void jumptoActivity(Context context, Class<?> _cls, String flag ) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        intent.putExtra(Constant.ACTIVITY_CREATE_FROM, flag);
        context.startActivity(intent);
    }
}
