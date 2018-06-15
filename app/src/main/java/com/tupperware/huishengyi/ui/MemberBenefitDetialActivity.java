package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.ui.fragment.MemberBenefitDetialFragment;
import com.android.dhunter.common.utils.SharePreferenceData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/2.
 */

public class MemberBenefitDetialActivity extends BaseActivity {

    private static final String TAG = "MemberBenefitDetialActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    private MemberBenefitDetialFragment mFragment;

    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private String mSelectTile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benefit_coin);  //复用
        mSharePreDate = new SharePreferenceData(this);
        token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
        mSelectTile = getIntent().getStringExtra(Constant.ACTIVITY_TITLE);
        mContext = this;
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mRightNext.setVisibility(View.GONE);
        mTitle.setText(getResources().getString(R.string.member_benefit_detial));
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ACTIVITY_TITLE, mSelectTile);
        mFragment = MemberBenefitDetialFragment.newInstance(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.benefitFrame, mFragment).commit();
    }

    @Override
    protected void initLayoutData() {

    }

    @OnClick(R.id.left_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
        }
    }

}
