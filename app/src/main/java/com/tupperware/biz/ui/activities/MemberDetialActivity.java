package com.tupperware.biz.ui.activities;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.baserecycleview.BaseQuickAdapter;
import com.android.dhunter.common.baserecycleview.BaseViewHolder;
import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.utils.ScreenUtil;
import com.android.dhunter.common.widget.flowlayout.FlowLayoutManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.ui.component.DaggerMemberDetialActivityComponent;
import com.tupperware.biz.ui.contract.MemberDetialContract;
import com.tupperware.biz.ui.module.MemberDetialPresenterModule;
import com.tupperware.biz.ui.presenter.MemberDetialPresenter;
import com.tupperware.biz.utils.DateFormatter;
import com.tupperware.biz.utils.ImageUrl;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/28.
 */

public class MemberDetialActivity extends BaseActivity implements MemberDetialContract.View {

    private static final String TAG = "MemberDetialActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.photo)
    SimpleDraweeView mPhoto;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.sex_flag)
    ImageView mSexFlag;
    @BindView(R.id.wechat_flag)
    ImageView mWechatFlag;
    @BindView(R.id.vip_flag)
    ImageView mVipFlag;
    @BindView(R.id.tel_num)
    TextView mTelNum;
    @BindView(R.id.coin_balance_num)
    TextView mCoinNum;
    @BindView(R.id.coupon_num)
    TextView mCouPonNum;
    @BindView(R.id.birthday)
    TextView mBirthday;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.gen_remarks)
    TextView mRemarks;
    @BindView(R.id.register_product_code)
    RelativeLayout mRegisterProductCode;
    @BindView(R.id.register_product_code_text)
    TextView mRegisterProductCodeText;
    @BindView(R.id.regiter_date)
    TextView mRegisterDate;
    @BindView(R.id.register_update_code)
    RelativeLayout mRegisterUpdateCode;
    @BindView(R.id.register_update_code_text)
    TextView mRegisterUpdateCodeText;
    @BindView(R.id.update_date)
    TextView mUpdateDate;
    @BindView(R.id.register_store_text)
    TextView mRegisterStoreText;
    @BindView(R.id.owner_store_text)
    TextView mOwnerStoreText;

    private Integer member_id;
    private String mobilePhone;
    private FlagAdapter mAdapter;
    private String mobileNum;
    private Integer storeId;
    private String employeeCode;
    private FlowLayoutManager flManager;

    @Inject
    MemberDetialPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_detial_new;
    }

    @Override
    protected void initLayout() {
        DaggerMemberDetialActivityComponent.builder()
                .appComponent(getAppComponent())
                .memberDetialPresenterModule(new MemberDetialPresenterModule(this, MemberDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        storeId = (Integer) mDataManager.getSpObjectData(GlobalConfig.STORE_ID, 0);
        employeeCode = mDataManager.getSPData(GlobalConfig.EMPLOYEE_CODE);
        member_id = getIntent().getIntExtra(Constant.MEMBER_ID, -1);
        mobilePhone = getIntent().getStringExtra(Constant.DEV_MEMBER_PHONE);
        mTitle.setText(getResources().getString(R.string.member_benefit_detial));
        mRightNext.setVisibility(View.GONE);
        flManager = new FlowLayoutManager(this);
        mRecyclerView.setLayoutManager(flManager);
        mAdapter = new FlagAdapter(R.layout.textview_tag_select);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class FlagAdapter extends BaseQuickAdapter<MemberBean.Extra.MemberTag, BaseViewHolder> {

        public FlagAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, MemberBean.Extra.MemberTag item, int position) {
            helper.setText(R.id.text_flag, item.tagName);
        }
    }

    @Override
    protected void requestData() {
//        if(mobilePhone != null && !mobilePhone.isEmpty()) {
//            mobileNum = mobilePhone;
//        } else if(member_id != 0){
//            mobileNum = member_id + "";
//        } else {
//            toast("电话号码为空");
//        }
        showDialog();
        mPresenter.getMemberDetialData(member_id, mobilePhone, storeId, employeeCode);
    }

    @OnClick({R.id.left_back, R.id.gen_remarks, R.id.reservation_server, R.id.logistics_schedule})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.gen_remarks:
                String remarks_content = mRemarks.getText().toString().trim();
                Intent intent = new Intent(this, RemarksActivity.class);
                intent.putExtra(Constant.MEMBER_PHONE, mobilePhone);
                intent.putExtra(Constant.MEMBER_ID, member_id);
                intent.putExtra(Constant.REMARKS_CONTENT, remarks_content);
                startActivityForResult(intent, Constant.REQUEST_REMARKS_CONTENT);
                break;
            case R.id.reservation_server:
                Intent resintent = new Intent(this, ReservationServerActivity.class);
                resintent.putExtra(Constant.MEMBER_PHONE, mobilePhone);
                startActivity(resintent);
                break;
            case R.id.logistics_schedule:
                Intent logintent = new Intent(this, GiftListActivity.class);
                logintent.putExtra(Constant.MEMBER_ID, member_id);
                startActivity(logintent);
                break;
//            case R.id.call:
//                telNum = mTelNum.getText().toString().trim();
//                try{
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telNum)); //拨号界面
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + telNum));  // 直接拨打
//                    startActivity(intent);
//                } catch (Exception e) {
//                    toast(getResources().getString(R.string.permission));
//                }
//
//                break;
        }
    }

    @Override
    public void refreshUIData(MemberBean memberbean) {
        if(memberbean!= null && memberbean.model != null) {
            member_id = memberbean.model.member_id;
            String url = ImageUrl.parseUrl(memberbean.model.avatar);
            if(mPhoto == null) {
                return;
            }
            mPhoto.setImageURI(url);
            mUserName.setText(memberbean.model.name);
            //性别标识
            if(memberbean.model.gender == 0) {
                mSexFlag.setImageResource(R.mipmap.meb_male_ic);
            } else if(memberbean.model.gender == 1) {
                mSexFlag.setImageResource(R.mipmap.meb_female_ic);
            } else {
                mSexFlag.setVisibility(View.GONE);
            }
            //vip标识
            if(memberbean.model.group_id == 1) {
                mVipFlag.setVisibility(View.VISIBLE);
            } else {
                mVipFlag.setVisibility(View.GONE);
            }
            //Wechat标识
            if(memberbean.getModel().getRegister_channel() != null && memberbean.getModel().getRegister_channel().equals("1")) {
                mWechatFlag.setVisibility(View.VISIBLE);
            } else {
                mWechatFlag.setVisibility(View.GONE);
            }
            mTelNum.setText(memberbean.model.mobile);
            mobilePhone = memberbean.model.mobile;
            mCoinNum.setText(memberbean.model.integral_amount + "");
            mCouPonNum.setText(memberbean.model.member_coupon_total + "");
            mBirthday.setText(memberbean.model.member_birthday);
            mRegisterProductCodeText.setText(memberbean.model.register_product_code);
            if(memberbean.model.reg_time == 0) {
                mRegisterDate.setText("");
            } else {
                mRegisterDate.setText("(" + new DateFormatter().timeSecondToDateDay(memberbean.model.reg_time) + ")");
            }
            mRegisterUpdateCodeText.setText(memberbean.model.upgrade_product_code);
            if(memberbean.model.upgrade_time == 0) {
                mUpdateDate.setText("");
            } else {
                mUpdateDate.setText("(" + new DateFormatter().timeSecondToDateDay(memberbean.model.upgrade_time) + ")");
            }
            mRegisterStoreText.setText(memberbean.model.register_store);
            mOwnerStoreText.setText(memberbean.model.current_store);
            mRemarks.setText(memberbean.model.member_remark);
            refreshFlag(memberbean.extra.memberTags);
        }
    }

    private void refreshFlag(List<MemberBean.Extra.MemberTag> memberTags) {

        int size = memberTags.size();
        if(size > 0) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp.height = ScreenUtil.dip2px(mContext, 35 * ((size / 4) + 1));
            lp.setMargins(ScreenUtil.dip2px(mContext, 14), 0, ScreenUtil.dip2px(mContext, 14), 0);
            mRecyclerView.setLayoutParams(lp);
            mAdapter.setNewData(memberTags);
        }
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mRecyclerView.getLayoutParams();
////        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        lp.height = flManager.getVerticalSpace();
//        mRecyclerView.setLayoutParams(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUEST_REMARKS_CONTENT) {
            if(Constant.REQUEST_REMARKS_CONTENT_RESULT == resultCode) {
                String remarks = data.getStringExtra(Constant.REMARKS_CONTENT);
                mRemarks.setText(remarks);
            }
        }
    }
}
