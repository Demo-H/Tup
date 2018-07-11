package com.tupperware.huishengyi.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.DevMemberSelect;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/5/31.
 */

public class DevCookSelectActivity extends BaseActivity {

    private static final String TAG = "DevCookSelectActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.like)
    TextView mLike;
    @BindView(R.id.study)
    TextView mStudy;
    @BindView(R.id.not_like)
    TextView mNotLike;
    @BindView(R.id.not_study)
    TextView mNotStudy;

    private int currentLikeSelPosition = -1; //默认全部不选中
    private int currentStudySelPosition = -1;
    private DevMemberSelect mDevMember;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_cook_select;
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

    @OnClick({R.id.left_back, R.id.next, R.id.like, R.id.study, R.id.not_like, R.id.not_study})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                if(currentLikeSelPosition == -1 || currentStudySelPosition == -1) {
                    toast("请选择后再进行下一步");
                } else {
                    Intent intent = new Intent(this, DevTasteSelectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DEV_MEMBER_DATA, mDevMember);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.like:
                selectLikeChange(mLike, 0);
                if(mDevMember != null) {
                    mDevMember.setIsLike(Constant.TAG_CODE_LIKE);
                }
                break;
            case R.id.not_like:
                selectLikeChange(mNotLike, 1);
                if(mDevMember != null) {
                    mDevMember.setIsLike(Constant.TAG_CODE_NOT_LIKE);
                }
                break;
            case R.id.study:
                selectStudyChange(mStudy, 2);
                if(mDevMember != null) {
                    mDevMember.setIsWantStudy(Constant.TAG_CODE_WANT_STUDY);
                }
                break;
            case R.id.not_study:
                selectStudyChange(mNotStudy, 3);
                if(mDevMember != null) {
                    mDevMember.setIsWantStudy(Constant.TAG_CODE_NOT_WANT_STUDY);
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

    private void selectLikeChange(TextView mTextView, int position) {
        if(mTextView.isSelected()) {
            mTextView.setSelected(false);
            currentLikeSelPosition = -1;
        } else {
            mTextView.setSelected(true);
            if(currentLikeSelPosition == 0) {
                mLike.setSelected(false);
            } else if(currentLikeSelPosition == 1) {
                mNotLike.setSelected(false);
            }
            currentLikeSelPosition = position;
        }
    }

    private void selectStudyChange(TextView mTextView, int position) {
        if(mTextView.isSelected()) {
            mTextView.setSelected(false);
            currentStudySelPosition = -1;
        } else {
            mTextView.setSelected(true);
            if(currentStudySelPosition == 2) {
                mStudy.setSelected(false);
            } else if(currentStudySelPosition == 3) {
                mNotStudy.setSelected(false);
            }
            currentStudySelPosition = position;
        }
    }
}
