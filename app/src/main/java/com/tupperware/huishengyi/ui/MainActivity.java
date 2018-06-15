package com.tupperware.huishengyi.ui;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.dhunter.common.config.GlobalConfig;
import com.android.dhunter.common.utils.FileUtils;
import com.android.dhunter.common.utils.NetworkUtil;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.adapter.HomeFragmentAdapter;
import com.tupperware.huishengyi.component.DaggerMainActivityComponent;
import com.tupperware.huishengyi.http.MainDataManager;
import com.tupperware.huishengyi.model.AppUpdateModel;
import com.tupperware.huishengyi.module.VersionCheckPresenterModule;
import com.tupperware.huishengyi.ui.IListener.IOnFragmentBackPressedListener;
import com.tupperware.huishengyi.ui.contract.VersionCheckContract;
import com.tupperware.huishengyi.ui.fragment.BusinessCollegeFragment;
import com.tupperware.huishengyi.ui.fragment.CloudOrderFragment;
import com.tupperware.huishengyi.ui.fragment.DataShowFragment;
import com.tupperware.huishengyi.ui.fragment.HomePageFragment;
import com.tupperware.huishengyi.ui.fragment.LoveVipFragment;
import com.tupperware.huishengyi.ui.presenter.VersionCheckPresenter;
import com.tupperware.huishengyi.utils.logutils.LogF;
import com.tupperware.huishengyi.view.HomeTabViewPager;
import com.tupperware.huishengyi.view.NoScrollViewPager;
import com.tupperware.huishengyi.widget.CustomDialog;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements VersionCheckContract.View {

    private static final String TAG = "MainActivity";

    private int mCurrentViewPagePosition = 0;//当前的页面
    private HomeTabViewPager mHomeView;
    private NoScrollViewPager mViewPager;
    private ArrayList<Fragment> mFragments;
    private HomeFragmentAdapter mFragmentAdapter;
//    public Toolbar mToolbar;

    private HomePageFragment mHomePageFragment;
    private LoveVipFragment mLoveVipFragment;
    private CloudOrderFragment mCloudOrderFragment;
//    private DataWindowFragment mDataWindowFragment;
    private DataShowFragment mDataShowFragment;
//    private MarketInfoFragment mMarketInfoFragment;
    private BusinessCollegeFragment mCollegeFragment;

    private AppUpdateModel mAppVersionCheck;
    private boolean isCheck = false;
    private String mUserId;

    @Inject
    VersionCheckPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawableResource(R.color.home_item_bg);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHomeView = (HomeTabViewPager) this.findViewById(R.id.home_tag_view_pager);
        LogF.i(TAG, "findViews()" + mHomeView);
        findViews();
        initLayout();
        checkVersion();
    }

    //接受推送信息
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void findViews() {
//        initToolBar();
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
//        setupToolBar(0);
    }

    @Override
    protected void initLayoutData() {

    }

//    private void initToolBar() {
//        mToolbar.setTitle(getResources().getString(R.string.app_name));
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.tv_title_color));
//        setSupportActionBar(mToolbar);
//    }

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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
            mUserId = (String) mSharePreDate.getParam(GlobalConfig.KEY_DATA_USERID, "");
            /** 采用okhttp3 **/
            mPresenter.checkVersion(mUserId);
        }

        /** 以下采用Volley */
//        Map<String, String> params = new HashMap<>();
//        params.put("userId", "29444");   // 测试用户名29444
//        params.put("version", "1.0.0");  //
//        mTupperware.post(Constants.REQUEST_CODE_CHECK_VERSION, ConfigURL.VERSION_CHECK, params, new Tupperware.TupperwareListener() {
//            @Override
//            public void ok(int requestCode, String json) {
//                String js = json;
//                Log.i("bingle","" + js);
//            }
//        } , new Tupperware.TupperwareErrorListener() {
//            @Override
//            public boolean error(int requestCode, ResponseBean errorCode) {
//                Log.i("bingle","" + errorCode.toString());
//                return false;
//            }
//        }, null);

    }

//    @Override
//    public void ok(int requestCode, String json) {
//        if(requestCode == Constants.REQUEST_CODE_CHECK_VERSION) {
//            mAppVersionCheck = AppUpdateModel.createInstanceByJson(json);
//            if(mAppVersionCheck == null) {
//                return;
//            }
//            String currentVersion = AppUtils.getVersionName();
//            if(mAppVersionCheck.getVersion().compareTo(currentVersion) > 0) {
//                showUpdateChooseDialog(mAppVersionCheck.getVersion());
//            }
//
//        }
//    }
//
//    @Override
//    public boolean error(int requestCode, ResponseBean responseBean) {
//        return false;
//    }

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
        FileUtils.downloadFileIsExists();
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(downloadurl);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        request.setMimeType("application/cn.trinea.download.file");
        File file = new File(FileUtils.DIR_DOWNLOAD);
        file.mkdirs();
        String[] strArray = downloadurl.split("/");
        String mDownloadFileName = "tupperware.apk";

        File f = new File(FileUtils.DIR_DOWNLOAD + File.separator + mDownloadFileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            request.setDestinationInExternalPublicDir(FileUtils.DIR_DOWNLOAD, mDownloadFileName);
            request.setTitle(mDownloadFileName);
            long myDownloadReference = downloadManager.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        SharePreferenceData mSharePreSetting = new SharePreferenceData(SharePreferenceData.BASE_APP_SETTING, this);
    }

}
