package com.tupperware.huishengyi.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.dhunter.common.utils.FileUtils;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.utils.BitmapUtils;
import com.tupperware.huishengyi.utils.logutils.LogF;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.dhunter.common.utils.FileUtils.DIR_PHOTO_CRASH;
import static com.tupperware.huishengyi.config.Constant.CAMERA_WITH_DATA;

/**
 * Created by dhunter on 2018/3/20.
 */

public class FeedBackActivity extends BaseActivity {

    private static final String TAG = "FeedBackActivity";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.right_text)
    TextView mRightText;

    @BindView(R.id.bad_goods)
    TextView mBadGoods;
    @BindView(R.id.miss_goods)
    TextView mMissGoods;
    @BindView(R.id.send_error_goods)
    TextView mSendErrorGoods;
    @BindView(R.id.feedback_content)
    EditText mFeedbackContent;
    @BindView(R.id.take_photo)
    ImageView mTakePhoto;
    @BindView(R.id.content_number)
    TextView mCountText;

    private int count;  //输入字数
    private String photoPath;

    private File mCurrentPhotoFile;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initLayout() {
        initToolBar();
        mFeedbackContent.addTextChangedListener(watcher);
    }

    @Override
    protected void requestData() {

    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            count = s.length();
            mCountText.setText(count + "/500");
        }
    };

    private void initToolBar() {
        mRightText.setText(getResources().getString(R.string.send));
        mTitle.setText(getResources().getString(R.string.feedback_send));
    }

    @OnClick({R.id.left_back, R.id.next, R.id.bad_goods, R.id.miss_goods, R.id.send_error_goods, R.id.take_photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.next:
                sendFeedBack();
                toast("您的反馈已发送成功");
                break;
            case R.id.bad_goods:
                toast("bad_goods");
                if(mBadGoods.isSelected()) {
                    mBadGoods.setSelected(false);
                } else {
                    mBadGoods.setSelected(true);
                }
                break;
            case R.id.miss_goods:
                if(mMissGoods.isSelected()) {
                    mMissGoods.setSelected(false);
                } else {
                    mMissGoods.setSelected(true);
                }
                break;
            case R.id.send_error_goods:
                if(mSendErrorGoods.isSelected()) {
                    mSendErrorGoods.setSelected(false);
                } else {
                    mSendErrorGoods.setSelected(true);
                }
                break;
            case R.id.take_photo:
                takePhoto();
                break;
        }
    }

    private void sendFeedBack() {}

    private void takePhoto() {
        if (!FileUtils.sdcardAvailable()) {
            toast(getResources().getString(R.string.sdNotAvailable));
            return;
        }
        try{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_CAMERA);
                return;
            }
            cameraCapture();
        } catch (Exception e) {
            toast("请打开使用摄像头权限");
        }
    }

    private void cameraCapture() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        String fileName = dateFormat.format(date) + ".jpg";
        mCurrentPhotoFile = new File(DIR_PHOTO_CRASH, fileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);


        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(mContext, "com.android.tupperware.fileprovider", mCurrentPhotoFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //适配小米，需要循环拿权限
            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, fileUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            fileUri = Uri.fromFile(mCurrentPhotoFile);
        }
        photoPath = DIR_PHOTO_CRASH + File.separator + fileName;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.putExtra("path", fileUri);
        intent.putExtra("outputX", 1024);
        intent.putExtra("outputY", 1024);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        try {
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            toast("没有获取到照片");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogF.d(TAG, "onActivityResult " + requestCode + ", " + resultCode + ", " + data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CAMERA_WITH_DATA:

                Bitmap smallBitmap = BitmapUtils.getSmallBitmap(photoPath);
                mTakePhoto.setImageBitmap(smallBitmap);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQ_PERM_CAMERA:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    cameraCapture();
                } else {
                    // 被禁止授权
                    toast("请至权限中心打开本应用的相机访问权限");
                }
                break;
        }
    }

}
