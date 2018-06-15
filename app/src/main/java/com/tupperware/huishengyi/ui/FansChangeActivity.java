package com.tupperware.huishengyi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.ui.fragment.MemberStatusFragment;
import com.android.dhunter.common.utils.SharePreferenceData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/4/3.
 */

public class FansChangeActivity extends BaseActivity {
    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.right_image)
    ImageView mRightIcon;
    @BindView(R.id.toolbar_title)
    TextView mToolTitle;

    private SharePreferenceData mSharePreDate;
    private String token;
    private Context mContext;
    private MemberStatusFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_zhaomu);  // 复用
        mSharePreDate = new SharePreferenceData(this);
        token =(String) mSharePreDate.getParam(Constants.KEY_DATA_TOKEN, "");
        mContext = this;
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        initToolBar();
        mFragment = new MemberStatusFragment();
        mFragment.setRightIconText(mRightNext);
        getSupportFragmentManager().beginTransaction().add(R.id.zhaomuFrame, mFragment).commit();
    }

    @Override
    protected void initLayoutData() {

    }

    private void initToolBar() {
        mRightText.setVisibility(View.GONE);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightIcon.setImageResource(R.mipmap.date_btn);
        mToolTitle.setText(getResources().getString(R.string.fans_change));
    }


    @OnClick({R.id.left_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
        }

    }
}
