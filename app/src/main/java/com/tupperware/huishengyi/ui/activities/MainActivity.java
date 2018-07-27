package com.tupperware.huishengyi.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.utils.FileUtils;
import com.android.dhunter.common.utils.NetworkUtil;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.HomeFragmentAdapter;
import com.tupperware.huishengyi.base.BaseActivity;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.listener.IOnFragmentBackPressedListener;
import com.tupperware.huishengyi.ui.component.DaggerMainActivityComponent;
import com.tupperware.huishengyi.ui.contract.VersionCheckContract;
import com.tupperware.huishengyi.ui.fragment.BusinessCollegeFragment;
import com.tupperware.huishengyi.ui.fragment.CloudOrderFragment;
import com.tupperware.huishengyi.ui.fragment.DataShowFragment;
import com.tupperware.huishengyi.ui.fragment.HomePageFragment;
import com.tupperware.huishengyi.ui.fragment.LoveVipFragment;
import com.tupperware.huishengyi.ui.module.VersionCheckPresenterModule;
import com.tupperware.huishengyi.ui.presenter.VersionCheckPresenter;
import com.tupperware.huishengyi.utils.UpdateUtils;
import com.tupperware.huishengyi.view.HomeTabViewPager;
import com.tupperware.huishengyi.view.NoScrollViewPager;
import com.tupperware.huishengyi.widget.CustomDialog;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements VersionCheckContract.View {

    private static final String TAG = "MainActivity";

    @BindView(R.id.home_tag_view_pager)
    HomeTabViewPager mHomeView;

    private int mCurrentViewPagePosition = 0;//当前的页面
    private NoScrollViewPager mViewPager;
    private ArrayList<Fragment> mFragments;
    private HomeFragmentAdapter mFragmentAdapter;

    private HomePageFragment mHomePageFragment;
    private LoveVipFragment mLoveVipFragment;
    private CloudOrderFragment mCloudOrderFragment;
//    private DataWindowFragment mDataWindowFragment;
    private DataShowFragment mDataShowFragment;
//    private MarketInfoFragment mMarketInfoFragment;
    private BusinessCollegeFragment mCollegeFragment;

    private boolean isCheck = false;
    private String mUserId;
    private long mId;
    private Dialog mProgressDialog;
    private ProgressBar mProgressBar;
    private TextView mPrecent;
    private TextView mComplete;

    @Inject
    VersionCheckPresenter mPresenter;
    private String mDownloadFileName = "tupperware.apk";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawableResource(R.color.home_item_bg);
        super.onCreate(savedInstanceState);
    }

    //接受推送信息
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void initLayout() {
        DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .versionCheckPresenterModule(new VersionCheckPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        mViewPager = mHomeView.getViewPager();
        mViewPager.setOffscreenPageLimit(4);

        initFragment();
    }

    @Override
    protected void requestData() {
        checkVersion();
    }


    //添加fragment
    private void initFragment() {
        mFragments = new ArrayList<Fragment>();

        mHomePageFragment = HomePageFragment.newInstance();
        mFragments.add(mHomePageFragment);

        mLoveVipFragment = LoveVipFragment.newInstance();
        mFragments.add(mLoveVipFragment);

        mCloudOrderFragment = CloudOrderFragment.newInstance();
        mFragments.add(mCloudOrderFragment);

//        mDataWindowFragment = DataWindowFragment.newInstance();
//        mFragments.add(mDataWindowFragment);
        mDataShowFragment = DataShowFragment.newInstance();
        mFragments.add(mDataShowFragment);

//        mMarketInfoFragment = MarketInfoFragment.newInstance();
//        mFragments.add(mMarketInfoFragment);
        mCollegeFragment = BusinessCollegeFragment.newInstance();
        mFragments.add(mCollegeFragment);

        mFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), mFragments);

        mViewPager.setAdapter(mFragmentAdapter);

    }

    @Override
    public void onBackPressed() {
        int index = mViewPager.getCurrentItem();
        Fragment fragment = mFragments.get(index);
        if (fragment instanceof IOnFragmentBackPressedListener) {
            ((IOnFragmentBackPressedListener) fragment).onBackPressed();
        } else {
            moveTaskToBack(true);
        }
    }


    public NoScrollViewPager getViewPager() {
        return mViewPager;
    }

    public int getMCurrentViewPagePosition() {
        return mCurrentViewPagePosition;
    }

    public HomeTabViewPager getHomeTabViewPager() {
        return mHomeView;
    }

    private void checkVersion() {
        if(NetworkUtil.isNetworkAvailable(this)) {
            mUserId = mDataManager.getSPData(GlobalConfig.KEY_DATA_USERID);
            mPresenter.checkVersion(mUserId);
        }

    }


    @Override
    public void showUpdateChooseDialog(String title, final String downloadurl) {
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.settitle("发现新版本：" + title);
        customDialog.setmessage("立即升级，有更多惊喜等着你哟~");
        customDialog.setCancelable(false);
        customDialog.setcancelText(getResources().getString(R.string.cancel));
        customDialog.setsureText(getResources().getString(R.string.download));
        customDialog.setCancelOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("请升级");
            }
        });
        customDialog.setSureOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownloadApk(downloadurl);
            }
        });
        customDialog.build().show();

    }


    private void startDownloadApk(String downloadurl) {
        downloadurl = "http://imtt.dd.qq.com/16891/4B61342F28FFAA79A313FCBC03AD238E.apk?fsname=com.huaban.android_4.0.8_83.apk&csr=1bbd";
        if (TextUtils.isEmpty(downloadurl)) {
            toast("无效的下载地址");
            finish();
        }
        FileUtils.fileIsExistsbyType(FileUtils.PathType.DOWNLOAD);
        UpdateUtils.startDownload(downloadurl, MainActivity.this);
//        FileUtils.fileIsExistsbyType(FileUtils.PathType.DOWNLOAD);
//        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(downloadurl);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//
//        //设置允许使用的网络类型，这里是移动网络和wifi都可以
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setVisibleInDownloadsUi(true);
//        request.setMimeType("application/cn.trinea.download.file");
//        File file = new File(FileUtils.DIR_DOWNLOAD);
//        file.mkdirs();
////        String[] strArray = downloadurl.split("/");
//
//        File f = new File(FileUtils.DIR_DOWNLOAD + File.separator + mDownloadFileName);
//        if (f.exists()) {
//            f.delete();
//        }
//        try {
//            request.setDestinationInExternalPublicDir(FileUtils.AVSOLUTE_DOWNLOAD_PATH, mDownloadFileName);
//            request.setTitle(mDownloadFileName);
//            mId = downloadManager.enqueue(request);
//
//            //注册内容观察者，实时显示进度
//            DownloadContentObserver downloadChangeObserver = new DownloadContentObserver(null);
//            getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadChangeObserver);
//
//            //广播监听下载完成
//            listener(mId);
//            //显示进度的对话框
//            mProgressDialog = new Dialog(MainActivity.this, R.style.Theme_AppCompat_Dialog_Alert);
//            View view = MainActivity.this.getLayoutInflater().inflate(R.layout.progress_dialog, null);
//            mProgressBar = (ProgressBar) view.findViewById(R.id.pb);
//            mPrecent = (TextView) view.findViewById(R.id.tv_precent);
//            mProgressDialog.setContentView(view);
//            mProgressDialog.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpdateUtils.onRelease();
    }
}
