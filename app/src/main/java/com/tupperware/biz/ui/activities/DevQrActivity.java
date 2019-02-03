package com.tupperware.biz.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.entity.BaseResponse;
import com.tupperware.biz.entity.WeChatFansInfo;
import com.tupperware.biz.entity.login.LoginInfo;
import com.tupperware.biz.entity.member.SendMessageRequest;
import com.tupperware.biz.http.PersonalDataManager;
import com.tupperware.biz.jpush.LocalBroadcastManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.utils.ActivityManager;
import com.tupperware.biz.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tupperware.biz.config.Constant.KEY_EXTRAS;
import static com.tupperware.biz.config.Constant.KEY_MESSAGE;
import static com.tupperware.biz.config.Constant.MESSAGE_RECEIVED_ACTION;

/**
 * Created by dhunter on 2018/6/1.
 * 会员标志之关注微信二维码
 */

public class DevQrActivity extends BaseActivity {

    private static final String TAG = "DevQrActivity";
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.personal_qr_layout)
    RelativeLayout mQrlayout;
    @BindView(R.id.right_text)
    TextView mRightText;
    @BindView(R.id.personal_qr_icon)
    SimpleDraweeView mQrImage;

    @BindView(R.id.fans_info_ll)
    LinearLayout mFansInfoll;
    @BindView(R.id.welcome_title_ll)
    LinearLayout mWelcomeTitlell;
    @BindView(R.id.welcome_img)
    ImageView mWelcomeStoreImage;
    @BindView(R.id.welcome_store)
    TextView mWelcomeStore;
    @BindView(R.id.add_fans_one)
    TextView mAddFansOne;
    @BindView(R.id.photo)
    SimpleDraweeView mUserPhoto;
    @BindView(R.id.fans_nike)
    TextView mFansNike;
    @BindView(R.id.fans_sex)
    ImageView mFansSex;
    @BindView(R.id.fans_addr)
    TextView mFansAddr;
    @BindView(R.id.send_msg)
    Button mSendMsg;
    @BindView(R.id.belong_store_fans_tip_ll)
    LinearLayout mBelongStorell;
    @BindView(R.id.belong_store_fans_tip)
    TextView mBelongStoreFansTip;
    @BindView(R.id.fans_tip_ll)
    LinearLayout mFansTipll;
    @BindView(R.id.member_type)
    TextView mMemberType;

    private String mobilePhone;
    private String qr_url;
    public boolean isFront = false;
    private MessageReceiver mMessageReceiver;
//    private boolean isFirstFans; // 第一次成为粉丝
    private boolean isFansSex;
    private int mFansCode; // 0新增粉丝，1粉丝已存在且属于当前门店，2粉丝已存在但属于其他门店
    private WeChatFansInfo fansInfo;
    private String mRedundantCurrentStoreName;
    private String mCurrentLoginStoreName;
    private int mGroupId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        registerMessageReceiver();  // used for receive msg
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_qr;
    }

    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        mTitle.setText(getResources().getString(R.string.develop_new_member));
        mRightText.setText(getResources().getString(R.string.jump_ignore));
        mQrlayout.setVisibility(View.VISIBLE);
        mFansInfoll.setVisibility(View.GONE);
    }

    @Override
    protected void requestData() {
        mobilePhone = this.getIntent().getStringExtra(Constant.DEV_MEMBER_PHONE);
        qr_url = this.getIntent().getStringExtra(Constant.DEV_MEMBER_RQ_CODE);
        mQrImage.setImageURI(qr_url);
        mFansCode = this.getIntent().getIntExtra(Constant.DEV_MEMBER_FANS_CODE,0);
        mRedundantCurrentStoreName = this.getIntent().getStringExtra(Constant.DEV_MEMBER_CURRENT_STORE);
        mGroupId = getIntent().getIntExtra(Constant.DEV_MEMBER_GROUP_ID, 2);  //默认为粉丝，会员类别 0普通1高级 2粉丝
        String json = mDataManager.getSPData(GlobalConfig.KEY_DATA_LOGIN_INFO);
        if(json == null) {
            return;
        }
        LoginInfo logininfo = LoginInfo.createInstanceByJson(json);
        if(logininfo.getExtra() != null) {
            mCurrentLoginStoreName = logininfo.getExtra().getStoreName();
        }
    }

    @OnClick({R.id.left_back, R.id.next, R.id.send_msg})
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
            case R.id.send_msg:
                PersonalDataManager.getInstance(mDataManager).sendMessageToWechat(new ErrorDisposableObserver<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse bean) {
                        if(bean.isSuccess()) {
                            toast("发送成功");
                        } else {
                            toast(bean.getMessage());
                            if(bean.getResultCode() != null && (bean.getResultCode().equals(StateCode.TOKEN_OUT_DATE_S)
                                    || bean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                                reLogin();
                                mDataManager.deleteSPData();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                }, getSendMsgRequest(mobilePhone, mFansCode));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFront = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isFront = false;
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    //重写返回按钮
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
        finish();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (Constant.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(Constant.KEY_MESSAGE);
                    String extras = intent.getStringExtra(Constant.KEY_EXTRAS);
                    fansInfo = WeChatFansInfo.createInstanceByJson(messge);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!StringUtils.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }

    private void setCostomMsg(String msg){
        if (isFront) {
//            toast(msg);
            refreshUI();
        }
    }

    private void refreshUI() {
        mRightText.setText(getResources().getString(R.string.next));
        mQrlayout.setVisibility(View.GONE);
        mFansInfoll.setVisibility(View.VISIBLE);
        if(fansInfo == null) {
            toast("更新粉丝信息为空");
            return;
        }
        if(fansInfo.getHeadimgurl() != null) {
            mUserPhoto.setImageURI(fansInfo.getHeadimgurl());
        }
        if(fansInfo.getNickname() != null) {
            mFansNike.setText(fansInfo.getNickname());
        } else {
            mFansNike.setText(" ");
        }
        if(fansInfo.getSex() == 1) {
            mFansSex.setImageResource(R.mipmap.meb_male_ic); //男性
        } else if(fansInfo.getSex() == 2) {
            mFansSex.setImageResource(R.mipmap.meb_female_ic);
        } else {
            mFansSex.setVisibility(View.GONE);
        }
        if(fansInfo.getCity() != null || fansInfo.getProvince() != null) {
            mFansAddr.setText(fansInfo.getProvince() + " " + fansInfo.getCity());
        } else if(fansInfo.getCountry() != null) {
            mFansAddr.setText(fansInfo.getCountry());
        } else {
            mFansAddr.setText(" ");
        }
        if(mFansCode == 0) {
            mWelcomeTitlell.setVisibility(View.VISIBLE);
            mSendMsg.setVisibility(View.VISIBLE);
            mBelongStorell.setVisibility(View.GONE);
            mFansTipll.setVisibility(View.GONE);
            if(mCurrentLoginStoreName != null) {
                mWelcomeStore.setText(mCurrentLoginStoreName);
            } else {
                mWelcomeStore.setText("暂无门店信息");
            }
        } else {
            mWelcomeTitlell.setVisibility(View.GONE);
            mSendMsg.setVisibility(View.GONE);
            mBelongStorell.setVisibility(View.VISIBLE);
            mFansTipll.setVisibility(View.VISIBLE);
            if(mRedundantCurrentStoreName != null) {
                mBelongStoreFansTip.setText(mRedundantCurrentStoreName);
            } else {
                mBelongStoreFansTip.setText("其他店");
            }
            if(mGroupId == 0) {
                mMemberType.setText("的会员");
            } else if(mGroupId == 1) {
                mMemberType.setText("的高级会员");
            } else {
                mMemberType.setText("的粉丝");
            }
        }
    }

    private SendMessageRequest getSendMsgRequest(String mobile, int code) {
        SendMessageRequest requestData = new SendMessageRequest();
        requestData.setCode(code);
        requestData.setMemberMobile(mobile);
        return requestData;
    }
}
