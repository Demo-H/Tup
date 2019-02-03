package com.tupperware.biz.ui.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.ui.fragment.ServerManagerFragment;
import com.tupperware.biz.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/30.
 */

public class ServerManagerActivity extends BaseActivity {

    private static final String TAG = "ServerManagerActivity";

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

    private ServerManagerFragment mFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_key_product;
    }

    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        initToolbar();
        mFragment = new ServerManagerFragment();
        mFragment.setRightIconSearch(mRightNext);
        getSupportFragmentManager().beginTransaction().add(R.id.keyproductFrame, mFragment).commit();
    }

    @Override
    protected void requestData() {

    }

    private void initToolbar() {
        mRightText.setVisibility(View.GONE);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(R.mipmap.order_search_ic);
        mTitle.setText(getResources().getString(R.string.server_follow));
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
        }
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }
}