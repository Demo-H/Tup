package com.tupperware.huishengyi.ui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.qrcode.zxing.QRCodeEncoder;
import com.android.dhunter.common.utils.ScreenUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.utils.ActivityManager;
import com.tupperware.huishengyi.utils.RxAsyncHelper;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Func1;

/**
 * Created by dhunter on 2018/3/29.
 */

public class ReservationQrActivity extends BaseActivity {

    private static final String TAG = "ReservationQrActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;

    @BindView(R.id.store_qr_icon)
    SimpleDraweeView mQrIcon;
    @BindView(R.id.store_num)
    TextView mStoreNum;

//    private SharePreferenceData mSharePreDate;
    private String storeId;
    private String fromTag;
    private Bitmap mQRCodeBitmap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservaion_qr;
    }

    @TargetApi(16)
    @Override
    protected void initLayout() {
        ActivityManager.getInstance().addActivity(this);
        storeId = mDataManager.getSPData(Constants.KEY_DATA_USERNAME);
        fromTag = getIntent().getStringExtra(Constant.ACTIVITY_CREATE_FROM);
        mTitle.setText(getResources().getString(R.string.reservation_mark));
//        try{
//            Bitmap bitmaplogo = BitmapFactory.decodeResource(this.getResources(), R.mipmap.logo);
//            mQRCodeBitmap = EncodingHandler.createQRImage(this, storeId, bitmaplogo);
//        } catch (Exception e) {
//
//        }
//        mQrIcon.setBackground(new BitmapDrawable(this.getResources(),mQRCodeBitmap));
        RxAsyncHelper helper = new RxAsyncHelper("");
        helper.runInThread(new Func1() {
            @Override
            public Bitmap call(Object o) {
                /**
                 * 同步创建指定前景色、指定背景色、带logo的二维码图片。该方法是耗时操作，请在子线程中调用。
                 *
                 * @param content         要生成的二维码图片内容
                 * @param size            图片宽高，单位为px
                 * @param foregroundColor 二维码图片的前景色
                 * @param backgroundColor 二维码图片的背景色
                 * @param logo            二维码图片的logo
                 */
                Bitmap bitmaplogo = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
                Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(storeId, ScreenUtil.dip2px(mContext, R.dimen.dimen_164dp),
                        Color.parseColor("#ff7000"), bitmaplogo);
                return bitmap;
            }
        }).runOnMainThread(new Func1<Bitmap,String>() {
            @Override
            public String call(Bitmap bitmap) {
                mQrIcon.setBackground(new BitmapDrawable(mContext.getResources(),bitmap));
                return null;
            }
        }).subscribe();
        mStoreNum.setText(storeId);
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.left_back, R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
            case R.id.next:
                Intent intent = new Intent(this, ReservationMarkActivity.class);
                intent.putExtra(Constant.ACTIVITY_CREATE_FROM, Constant.RESERVATION_QR);
                intent.putExtra(Constant.MODULT_FROM, fromTag);
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
