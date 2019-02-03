package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.DevMemberSelect;
import com.tupperware.biz.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/1.
 * 会员标签之冰箱整理
 */

public class DevRefrigeratorStyleActivity extends BaseActivity {
    private static final String TAG = "DevPurifierSelectActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.good_style)
    SimpleDraweeView mGoodImage;
    @BindView(R.id.bad_style)
    SimpleDraweeView mBadImage;

    private int currentSelPosition = -1; //默认全部不选中
    private DevMemberSelect mDevMember;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_style_select;
    }

    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        mTitle.setText(getResources().getString(R.string.member_label));

    }

    @Override
    protected void requestData() {
        Bundle bundle = this.getIntent().getExtras();
        mDevMember = (DevMemberSelect)bundle.getSerializable(Constant.DEV_MEMBER_DATA);
    }

    @OnClick({R.id.left_back, R.id.next, R.id.good_style, R.id.bad_style})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                if(currentSelPosition == -1) {
                    toast("请选择后再进行下一步");
                } else {
                    Intent intent = new Intent(this, DevAttributeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DEV_MEMBER_DATA, mDevMember);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.good_style:
                selectChange(mGoodImage, 0);
                if(mDevMember != null) {
                    mDevMember.setIsClean(Constant.TAG_CODE_CLEAN);
                }
                break;
            case R.id.bad_style:
                selectChange(mBadImage, 1);
                if(mDevMember != null) {
                    mDevMember.setIsClean(Constant.TAG_CODE_NOT_CLEAN);
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

    private void selectChange(SimpleDraweeView mImage, int position) {
        if(mImage.isSelected()) {
            mImage.setSelected(false);
            currentSelPosition = -1;
            setSelect(mImage, false);
        } else {
            mImage.setSelected(true);
            setSelect(mImage, true);
            if(currentSelPosition == 0) {
                mGoodImage.setSelected(false);
                setSelect(mGoodImage, false);
            } else if(currentSelPosition == 1) {
                mBadImage.setSelected(false);
                setSelect(mBadImage, false);
            }
            currentSelPosition = position;
        }
    }

    private void setSelect(SimpleDraweeView mImage, boolean isSelected) {
        RoundingParams roundingParams = mImage.getHierarchy().getRoundingParams();
        if(!isSelected) {
            roundingParams.setBorderColor(0x00000000);
            mImage.getHierarchy().setRoundingParams(roundingParams);
        } else {
            roundingParams.setBorderColor(0xffff7000);
            mImage.getHierarchy().setRoundingParams(roundingParams);
        }
    }
}
