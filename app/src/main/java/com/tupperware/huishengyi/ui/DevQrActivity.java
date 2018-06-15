package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/1.
 * 会员标志之关注微信二维码
 */

public class DevQrActivity extends BaseActivity{

    private static final String TAG = "DevQrActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.personal_qr_icon)
    SimpleDraweeView mQrImage;
    private String mobilePhone;
    private String qr_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_qr);
        ActivityManager.getInstance().addActivity(this);
        initLayout();
        initLayoutData();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.develop_new_member));
        mRightText.setText(getResources().getString(R.string.jump_ignore));
    }

    @Override
    protected void initLayoutData() {
        mobilePhone = this.getIntent().getStringExtra(Constant.DEV_MEMBER_PHONE);
        qr_url = this.getIntent().getStringExtra(Constant.DEV_MEMBER_RQ_CODE);
        mQrImage.setImageURI(qr_url);
    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                Intent intent = new Intent(this, MemberDetialActivity.class);
                intent.putExtra(Constant.DEV_MEMBER_PHONE, mobilePhone);
                startActivity(intent);
                ActivityManager.getInstance().exit();
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
