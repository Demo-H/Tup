package com.tupperware.biz.ui.activities;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.PullHeaderView;
import com.android.dhunter.common.widget.pulltorefresh.PtrFrameLayout;
import com.android.dhunter.common.widget.pulltorefresh.PtrHandler;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.ActionMembersBean;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.ui.component.DaggerReservationActionActivityComponent;
import com.tupperware.biz.ui.contract.ReservationActionContract;
import com.tupperware.biz.ui.module.ReservationActionPresenterModule;
import com.tupperware.biz.ui.presenter.ReservationActionPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/14.
 */

public class ReservationActionActivity extends BaseActivity implements ReservationActionContract.View,
        PtrHandler {

    private static final String TAG = "ReservationActionActivity";

    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.find_pull_refresh_header)
    PullHeaderView mRefreshHeader;

    @BindView(R.id.server_title)
    TextView mServerTitle;
    @BindView(R.id.server_host_name)
    TextView mUserName;
    @BindView(R.id.contact_tel)
    TextView mTelphone;
    @BindView(R.id.join_num)
    TextView mJoinNum;
    @BindView(R.id.delicacy)
    TextView mDelicacy;
    @BindView(R.id.play_times)
    TextView mTime;

    private long action_member_id; //活动邀约名单界面进入

    @Inject
    ReservationActionPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_action;
    }

    @Override
    protected void initLayout() {
        action_member_id = getIntent().getLongExtra(Constant.ACTION_MEMBER_ID, 0);
        mTitle.setText(getResources().getString(R.string.reservation_server));
        mRightNext.setVisibility(View.GONE);
        DaggerReservationActionActivityComponent.builder()
                .appComponent(getAppComponent())
                .reservationActionPresenterModule(new ReservationActionPresenterModule(this, PersonalDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mRefreshHeader.setPtrHandler(this);
    }

    @Override
    protected void requestData() {
        showDialog();
        mPresenter.getMemberActionDetialData(action_member_id);
    }


    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getMemberActionDetialData(action_member_id);
                frame.refreshComplete();
            }
        }, 2000);
    }

    @OnClick({R.id.left_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
        }
    }

    @Override
    public void setMemberActionDetialData(ActionMembersBean mBean) {
        if(mBean != null && mBean.getModel() != null) {
            mServerTitle.setText(mBean.getModel().getTitle());
            mUserName.setText(mBean.getModel().getName());
            mTelphone.setText(mBean.getModel().getMobile());
            mJoinNum.setText(mBean.getModel().getNum() + "");
            mDelicacy.setText(mBean.getModel().getIntention());
            mTime.setText(mBean.getModel().getSessionTime());
        }
    }


}
