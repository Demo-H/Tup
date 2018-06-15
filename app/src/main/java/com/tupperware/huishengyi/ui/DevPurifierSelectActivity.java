package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.DevMemberSelect;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/1.
 * 会员标签之饮水机选择
 */

public class DevPurifierSelectActivity extends BaseActivity {

    private static final String TAG = "DevPurifierSelectActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.have)
    TextView mHava;
    @BindView(R.id.not_have)
    TextView mNotHave;

    private int currentSelPosition = -1; //默认全部不选中
    private DevMemberSelect mDevMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_purifier_select);
        ActivityManager.getInstance().addActivity(this);
        initLayout();
        initLayoutData();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.member_label));

    }

    @Override
    protected void initLayoutData() {
        Bundle bundle = this.getIntent().getExtras();
        mDevMember = (DevMemberSelect)bundle.getSerializable(Constant.DEV_MEMBER_DATA);
    }

    @OnClick({R.id.left_back, R.id.next, R.id.have, R.id.not_have})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                if(currentSelPosition == -1) {
                    toast("请选择后再进行下一步");
                } else {
                    Intent intent = new Intent(this, DevRefrigeratorStyleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DEV_MEMBER_DATA, mDevMember);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.have:
                selectChange(mHava, 0);
                if(mDevMember != null) {
                    mDevMember.setIsHave(Constant.TAG_CODE_HAVE_WATER);
                }
                break;
            case R.id.not_have:
                selectChange(mNotHave, 1);
                if(mDevMember != null) {
                    mDevMember.setIsHave(Constant.TAG_CODE_NOT_HAVE_WATER);
                }
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

    private void selectChange(TextView mTextView, int position) {
        if(mTextView.isSelected()) {
            mTextView.setSelected(false);
            currentSelPosition = -1;
        } else {
            mTextView.setSelected(true);
            if(currentSelPosition == 0) {
                mHava.setSelected(false);
            } else if(currentSelPosition == 1) {
                mNotHave.setSelected(false);
            }
            currentSelPosition = position;
        }
    }
}