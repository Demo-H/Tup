package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.DevMemberSelect;
import com.tupperware.huishengyi.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/6/1.
 * 会员标签之会员属性
 */

public class DevAttributeActivity extends BaseActivity {

    private static final String TAG = "DevAttributeActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.singler)
    SimpleDraweeView mSinglerImage;
    @BindView(R.id.singler_text)
    TextView mSinglerText;
    @BindView(R.id.lover)
    SimpleDraweeView mLoverImage;
    @BindView(R.id.lover_text)
    TextView mLoverText;
    @BindView(R.id.child)
    SimpleDraweeView mChildImage;
    @BindView(R.id.child_text)
    TextView mChildText;
    @BindView(R.id.family)
    SimpleDraweeView mFamilyImage;
    @BindView(R.id.family_text)
    TextView mFamilyText;

    @BindView(R.id.general_income)
    LinearLayout mGeneralIncomeLl;
    @BindView(R.id.general_income_text)
    TextView mIncomeText;
    @BindView(R.id.general_income_money)
    TextView mIncomeMoney;
    @BindView(R.id.small_rich)
    LinearLayout mSmallRichRl;
    @BindView(R.id.small_rich_text)
    TextView mSmallRichText;
    @BindView(R.id.small_rich_money)
    TextView mSmallTichMoney;
    @BindView(R.id.middle_rich)
    LinearLayout mMiddleRichRl;
    @BindView(R.id.middle_rich_text)
    TextView mMiddleRichText;
    @BindView(R.id.middle_rich_money)
    TextView mMiddleRichMoney;
    @BindView(R.id.very_rich)
    LinearLayout mMostRichRl;
    @BindView(R.id.very_rich_text)
    TextView mMostRichText;
    @BindView(R.id.very_rich_money)
    TextView mMostRichMoney;

    private int currentSelPosition_family = -1; //默认全部不选中
    private int currentSelPosition_income = -1; //默认全部不选中
    private DevMemberSelect mDevMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_attribute);
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

    @OnClick({R.id.left_back, R.id.next, R.id.singler, R.id.lover, R.id.child, R.id.family,
            R.id.general_income, R.id.small_rich, R.id.middle_rich, R.id.very_rich})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                if(currentSelPosition_family == -1 || currentSelPosition_income == -1) {
                    toast("请选择后再进行下一步");
                } else {
                    Intent intent = new Intent(this, DevProductSelectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DEV_MEMBER_DATA, mDevMember);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.singler:
                selectImageChange(mSinglerImage, mSinglerText, 0);
                if(mDevMember != null) {
                    mDevMember.setFamilystatus(Constant.TAG_CODE_SINGER);
                }
                break;
            case R.id.lover:
                selectImageChange(mLoverImage, mLoverText, 1);
                if(mDevMember != null) {
                    mDevMember.setFamilystatus(Constant.TAG_CODE_LOVE);
                }
                break;
            case R.id.child:
                selectImageChange(mChildImage, mChildText, 2);
                if(mDevMember != null) {
                    mDevMember.setFamilystatus(Constant.TAG_CODE_CHILD);
                }
                break;
            case R.id.family:
                selectImageChange(mFamilyImage, mFamilyText, 3);
                if(mDevMember != null) {
                    mDevMember.setFamilystatus(Constant.TAG_CODE_FAMILY);
                }
                break;
            case R.id.general_income:
                selectChange(mGeneralIncomeLl, mIncomeText, mIncomeMoney, 0);
                if(mDevMember != null) {
                    mDevMember.setMoneystatus(Constant.TAG_CODE_GENERAL);
                }
                break;
            case R.id.small_rich:
                selectChange(mSmallRichRl, mSmallRichText, mSmallTichMoney, 1);
                if(mDevMember != null) {
                    mDevMember.setMoneystatus(Constant.TAG_CODE_SMALL_RICH);
                }
                break;
            case R.id.middle_rich:
                selectChange(mMiddleRichRl, mMiddleRichText, mMiddleRichMoney, 2);
                if(mDevMember != null) {
                    mDevMember.setMoneystatus(Constant.TAG_CODE_MIDDLE_RICH);
                }
                break;
            case R.id.very_rich:
                selectChange(mMostRichRl, mMostRichText, mMostRichMoney, 3);
                if(mDevMember != null) {
                    mDevMember.setMoneystatus(Constant.TAG_CODE_VERY_RICH);
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

    private void selectChange(LinearLayout mLinearLayout, TextView mTextView, TextView mSubText, int position) {
        if(mLinearLayout.isSelected()) {
            mLinearLayout.setSelected(false);
            mTextView.setSelected(false);
            mSubText.setSelected(false);
            currentSelPosition_income = -1;
        } else {
            mLinearLayout.setSelected(true);
            mTextView.setSelected(true);
            mSubText.setSelected(true);
            if(currentSelPosition_income == 0) {
                mGeneralIncomeLl.setSelected(false);
                mIncomeText.setSelected(false);
                mIncomeMoney.setSelected(false);
            } else if(currentSelPosition_income == 1) {
                mSmallRichRl.setSelected(false);
                mSmallRichText.setSelected(false);
                mSmallTichMoney.setSelected(false);
            } else if(currentSelPosition_income == 2) {
                mMiddleRichRl.setSelected(false);
                mMiddleRichText.setSelected(false);
                mMiddleRichMoney.setSelected(false);
            } else if(currentSelPosition_income == 3) {
                mMostRichRl.setSelected(false);
                mMostRichText.setSelected(false);
                mMostRichMoney.setSelected(false);
            }
            currentSelPosition_income = position;
        }
    }

    private void selectImageChange(SimpleDraweeView mImage, TextView mSubText, int position) {
        if(mImage.isSelected()) {
            mImage.setSelected(false);
            mSubText.setSelected(false);
            currentSelPosition_family = -1;
            setSelect(mImage, false);
        } else {
            mImage.setSelected(true);
            mSubText.setSelected(true);
            setSelect(mImage, true);
            if(currentSelPosition_family == 0) {
                mSinglerImage.setSelected(false);
                setSelect(mSinglerImage, false);
                mSinglerText.setSelected(false);
            } else if(currentSelPosition_family == 1) {
                mLoverImage.setSelected(false);
                setSelect(mLoverImage, false);
                mLoverText.setSelected(false);
            } else if(currentSelPosition_family == 2) {
                mChildImage.setSelected(false);
                setSelect(mChildImage, false);
                mChildText.setSelected(false);
            } else if(currentSelPosition_family == 3) {
                mFamilyImage.setSelected(false);
                setSelect(mFamilyImage, false);
                mFamilyText.setSelected(false);
            }
            currentSelPosition_family = position;
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
