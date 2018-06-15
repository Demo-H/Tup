package com.tupperware.huishengyi.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.base.baseadapter.BaseQuickAdapter;
import com.android.dhunter.common.base.baseadapter.BaseViewHolder;
import com.android.dhunter.common.widget.flowlayout.FlowLayoutManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.entity.member.MemberBean;
import com.tupperware.huishengyi.network.ServerURL;
import com.tupperware.huishengyi.network.TupVolley;
import com.tupperware.huishengyi.utils.DateFormatter;
import com.tupperware.huishengyi.utils.ImageUrl;
import com.tupperware.huishengyi.utils.logutils.LogF;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/28.
 */

public class MemberDetialActivity extends BaseActivity implements TupVolley.TupVolleyErrorListener, TupVolley.TupVolleyListener {

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

    private long member_id;
    private String mobilePhone;
    private FlagAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detial_new);
        member_id = getIntent().getLongExtra(Constant.MEMBER_ID, 0);
        mobilePhone = getIntent().getStringExtra(Constant.DEV_MEMBER_PHONE);
        initLayout();
        initLayoutData();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        mTitle.setText(getResources().getString(R.string.member_benefit_detial));
        mRightNext.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new FlowLayoutManager());
        mAdapter = new FlagAdapter(R.layout.textview_tag_select);
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.textview_tag_select, getDatas()) {
//            @Override
//            protected void convert(BaseViewHolder helper, String item, int position) {
//                helper.setText(R.id.text_flag, item);
//            }
//        });

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

    public List<String> getDatas() {
        List<String> datas = new ArrayList<>();
        datas.add("家庭主妇");
        datas.add("二胎");
        datas.add("无辣不欢");
        return datas;
    }

    @Override
    protected void initLayoutData() {
        Uri.Builder builder = Uri.parse(ServerURL.MEMBER_INFO).buildUpon();
        if(mobilePhone != null && !mobilePhone.isEmpty()) {
            builder.appendQueryParameter("mobile", mobilePhone);
        } else {
            builder.appendQueryParameter("mobile", member_id + "");
        }
        String url = builder.toString();
        mTupVolley.get(Constants.REQUEST_CODE_MEMBER_DETIAL, url, this, this, headerparams);
        showDialog();
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
    public void ok(int requestCode, String json) {
        LogF.i(TAG, "json" + json);
        MemberBean memberbean = MemberBean.createInstanceByJson(json);
        hideDialog();
        if (!memberbean.isSuccess()) {
            toast(memberbean.getMessage());
            return;
        }
        refreshUIData(memberbean);
    }

    @Override
    public boolean error(int requestCode, ResponseBean errorCode) {
        hideDialog();
        return false;
    }

    private void refreshUIData(MemberBean memberbean) {
        if(memberbean!= null && memberbean.model != null) {
            member_id = memberbean.model.member_id;
            String url = ImageUrl.parseUrl(memberbean.model.avatar);
            mPhoto.setImageURI(url);
            mUserName.setText(memberbean.model.name);
            //性别标识
            if(memberbean.model.gender == 0) {
                mSexFlag.setImageResource(R.mipmap.meb_male_ic);
            } else {
                mSexFlag.setImageResource(R.mipmap.meb_female_ic);
            }
            //vip标识
            if(memberbean.model.group_id == 1) {
                mVipFlag.setVisibility(View.VISIBLE);
            } else {
                mVipFlag.setVisibility(View.GONE);
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
                mRegisterDate.setText("(" + new DateFormatter().timestampToDate(memberbean.model.reg_time) + ")");
            }
            mRegisterUpdateCodeText.setText(memberbean.model.upgrade_product_code);
            if(memberbean.model.upgrade_time == 0) {
                mUpdateDate.setText("");
            } else {
                mUpdateDate.setText("(" + new DateFormatter().timestampToDate(memberbean.model.upgrade_time) + ")");
            }
            mRegisterStoreText.setText(memberbean.model.register_store);
            mOwnerStoreText.setText(memberbean.model.current_store);
            mRemarks.setText(memberbean.model.member_remark);
            refreshFlag(memberbean.extra.memberTags);
        }
    }

    private void refreshFlag(List<MemberBean.Extra.MemberTag> memberTags) {
        mAdapter.setNewData(memberTags);
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
