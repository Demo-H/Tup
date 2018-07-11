package com.tupperware.huishengyi.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.ui.fragment.PurposeFollowFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/30.
 * 意向客户跟进界面
 */

public class PurposeFollowActivity extends BaseActivity {

    private static final String TAG = "PurposeFollowActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.right_image)
    ImageView mRightImage;

    private PurposeFollowFragment mFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_key_product;
    }

    @Override
    protected void initLayout() {
        initToolbar();
        mFragment = new PurposeFollowFragment();
//        mFragment.setRightIconSearch(mRightNext);
        getSupportFragmentManager().beginTransaction().add(R.id.keyproductFrame, mFragment).commit();
    }

    @Override
    protected void requestData() {

    }

    private void initToolbar() {
        mRightText.setVisibility(View.GONE);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(R.mipmap.order_search_ic);
        mTitle.setText(getResources().getString(R.string.vip_follow));
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                Intent intent = new Intent(PurposeFollowActivity.this, SearchActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.LOVE_VIP_FRAGMENT);
                startActivity(intent);
        }
    }
}
