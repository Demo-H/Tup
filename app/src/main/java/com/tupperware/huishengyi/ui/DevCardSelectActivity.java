package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/4.
 */

public class DevCardSelectActivity extends BaseActivity {

    private static final String TAG = "DevCookSelectActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_card_select);
        ActivityManager.getInstance().addActivity(this);
        initLayout();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.member_label));
        mRightText.setText(getResources().getString(R.string.jump_ignore));
    }

    @Override
    protected void initLayoutData() {

    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                Intent intent = new Intent(this, DevTasteSelectActivity.class);
                startActivity(intent);
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
