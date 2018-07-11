package com.tupperware.huishengyi.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.MemberBenefitDetialAdapter;
import com.tupperware.huishengyi.adapter.MemberCouponDetialAdapter;
import com.tupperware.huishengyi.base.BaseFragment;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.BenefitCoinExpenditureBean;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.ui.component.DaggerMemberBenefitDetialFragmentComponent;
import com.tupperware.huishengyi.ui.module.MemberBenefitDetialPresenterModule;
import com.tupperware.huishengyi.ui.contract.MemberBenefitDetialContract;
import com.tupperware.huishengyi.ui.presenter.MemberBenefitDetialPresenter;
import com.tupperware.huishengyi.view.SpacesItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/2.
 */

public class MemberBenefitDetialFragment extends BaseFragment implements MemberBenefitDetialContract.View {

    private static final String TAG = "MemberBenefitDetialFragment";

    @BindView(R.id.photo)
    ImageView mPhoto;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.tel_num)
    TextView mTelNum;

    @BindView(R.id.coupon_detial_title_ll)
    RelativeLayout mCouponTilterl;

    @BindView(R.id.benefit_detial_title_rl)
    RelativeLayout mBenefitTitleRl;
    @BindView(R.id.benefit_detial_content_ll)
    LinearLayout mBenefitContentll;
    @BindView(R.id.total_count)
    TextView mTotalCount;
    @BindView(R.id.deadline_time)
    TextView mDeadTime;

    @BindView(R.id.benefit_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.call)
    TextView mCall;
    @BindView(R.id.sms)
    TextView mSms;
    @BindView(R.id.wechat)
    TextView mChat;

    @Inject
    MemberBenefitDetialPresenter mPresenter;

    private MemberBenefitDetialAdapter mBenAdapter;
    private MemberCouponDetialAdapter mCouAdapter;

    private String mSelectTile;
    private String telNum;

    public static MemberBenefitDetialFragment newInstance(Bundle bundle) {
        MemberBenefitDetialFragment fragment = new MemberBenefitDetialFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mSelectTile = bundle.getString(Constant.ACTIVITY_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initLayout();
        requestData();
        return view;
    }

    @Override
    public void initLayout() {

        DaggerMemberBenefitDetialFragmentComponent.builder()
                .appComponent(getAppComponent())
                .memberBenefitDetialPresenterModule(new MemberBenefitDetialPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dimen_1dp);


        if(Constant.COUPON.equals(mSelectTile)) {
            mCouponTilterl.setVisibility(View.VISIBLE);
            mBenefitTitleRl.setVisibility(View.GONE);
            mBenefitContentll.setVisibility(View.GONE);
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_NO_TOP));
            mCouAdapter = new MemberCouponDetialAdapter(R.layout.item_member_coupon_detial_recyclerview);
            mRecyclerView.setAdapter(mCouAdapter);
        } else if(Constant.BENEFIT.equals(mSelectTile)) {
            mCouponTilterl.setVisibility(View.GONE);
            mBenefitTitleRl.setVisibility(View.VISIBLE);
            mBenefitContentll.setVisibility(View.VISIBLE);
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels, Constant.TYPE_ORDER));
            mBenAdapter = new MemberBenefitDetialAdapter(R.layout.item_member_benefit_detial_recyclerview);
            mRecyclerView.setAdapter(mBenAdapter);
        }

    }

    @Override
    public void requestData() {
        mPresenter.getMemberBenefitDetialData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_member_benefit_coin_detial;
    }

    @Override
    public void setMemberBenefitDetialData(BenefitCoinExpenditureBean benefitCoinExpenditure) {
        if(Constant.COUPON.equals(mSelectTile)) {
            mCouAdapter.addData(benefitCoinExpenditure.content);
        } else if(Constant.BENEFIT.equals(mSelectTile)) {
            mBenAdapter.addData(benefitCoinExpenditure.content);
        }
    }

    @OnClick({R.id.call, R.id.sms, R.id.wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call:
                telNum = mTelNum.getText().toString().trim();
                try{
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telNum)); //拨号界面
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + telNum));  // 直接拨打
                    startActivity(intent);
                } catch (Exception e) {
                    toast(getResources().getString(R.string.permission));
                }

                break;
            case R.id.sms:
                telNum = mTelNum.getText().toString().trim();
                try {
                    Uri uri = Uri.parse("smsto:" + telNum);
                    Intent intentMessage = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intentMessage);
                } catch (Exception e) {
                    toast(getResources().getString(R.string.permission));
                }
                break;
            case R.id.wechat:
                break;
        }
    }
}
