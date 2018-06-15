package com.tupperware.huishengyi.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.dhunter.common.widget.goodView.GoodView;
import com.tupperware.huishengyi.R;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.Constants;
import com.tupperware.huishengyi.entity.college.LikeBean;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.network.ConfigURL;
import com.tupperware.huishengyi.network.Tupperware;
import com.tupperware.huishengyi.utils.logutils.LogF;

import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/9.
 */

public class BrowserActivity extends BaseActivity implements Tupperware.TupperwareErrorListener, Tupperware.TupperwareListener {

    private static final String TAG = BrowserActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private LinearLayout mLeftBack;
    private TextView mToolTitle;
    private LinearLayout mRightNext;
    private TextView mRightText;
    private ImageView mRightIcon;
//    private String mTitleType;  //toolbar类型

    private WebView mWebView;
    private boolean isNeedRequest = true;

    @BindView(R.id.support_like)
    LinearLayout mSupportLl;
    @BindView(R.id.like)
    ImageView mLikeView;
    @BindView(R.id.like_count)
    TextView mLikeCount;
    private GoodView mGoodView;

    private boolean mInLoad;
    private boolean mPageStarted;
    private boolean mActivityInPause = true;
    private String mTitleName = "浏览";
    private String url_receive;
    private String url_type;
    private long answerId; //对应问答的id
    private int likeNum;
    private boolean isLike;
    private ProgressBar progressbar;
    private ImageButton mRefresh;
    private Context mContext;
    protected boolean mIsError = true; // 网页是否加载错误
    private RelativeLayout mEReloadLayout; //错误页面提示
    private ImageView mEImage; //错误页面提示图片
    private TextView mEText; //错误页面提示文字
    private LikeBean mLikeBean;
    private ResponseBean responseBean;
    private boolean isNeedCookie = false;
    private String cookie;

    private ValueCallback<Uri> mUploadMessage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_with_toolbar);
        initLayout();
    }

    @OnClick({R.id.left_back, R.id.like})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
//            case R.id.next:
//                toast("share");
//                share();
//                break;
            case R.id.like:
                clickLike();
                break;
        }

    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initLayout() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        if(bundle == null) {
//            url_receive = intent.getStringExtra(Constant.OPEN_URL);
//            mTitleName = intent.getStringExtra(Constant.URL_TITLE);
//        } else {
//            url_receive = bundle.getString(Constant.OPEN_URL);
//            mTitleName = bundle.getString(Constant.URL_TITLE);
//            url_type = bundle.getString(Constant.URL_TYPE);
//            answerId = bundle.getLong(Constant.ANSWER_ID);
//            likeNum = bundle.getInt(Constant.LIKE_COUNT);
//            getIsLike();
//        }
        if(bundle != null) {
            url_receive = bundle.getString(Constant.OPEN_URL);
            mTitleName = bundle.getString(Constant.URL_TITLE);
            url_type = bundle.getString(Constant.URL_TYPE);
            answerId = bundle.getLong(Constant.ANSWER_ID);
            likeNum = bundle.getInt(Constant.LIKE_COUNT);
            if(Constant.SUPPORT_LIKE.equals(url_type)) {
                getIsLike();
            }
            isNeedCookie = bundle.getBoolean(Constant.NEED_COOKIE);
//            if(isNeedCookie) {
//                cookie = (String) mSharePreDate.getParam(GlobalConfig.COOKIE, "");
//                CookieManager.getInstance().setCookie(url_receive, cookie);
//            }
        }

        if (!TextUtils.isEmpty(url_receive)) {
            if(url_receive.startsWith("http") || url_receive.startsWith("https")) {
                url_receive = url_receive.trim();
            } else {
                url_receive = "http://" + url_receive.trim();
            }
        }

//        mTitleType = intent.getStringExtra("title_type");
        progressbar = (ProgressBar) findViewById(R.id.progressBar1);
        mWebView = (WebView) findViewById(R.id.webView1);
        WebViewChromeClient webChromeClient = new WebViewChromeClient();
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);// 支持缩放
        mWebView.getSettings().setBuiltInZoomControls(true);// 显示放大缩小
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir()
                .getAbsolutePath();
        mWebView.getSettings().setAppCachePath(appCachePath);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        mWebView.addJavascriptInterface(new InJavaScriptInterface(), "local_method");

        initToolBar();

        mEReloadLayout = (RelativeLayout)findViewById(R.id.reloading_vm);
        mEImage = (ImageView) findViewById(R.id.reloadingimg);
        mEText = (TextView) findViewById(R.id.reloadingtx);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogF.i(TAG,"shouldOverrideUrlLoading");
                mWebView.loadUrl(url);
                return true;
//                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                LogF.i(TAG,"onLoadResource");
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                LogF.i(TAG,"onPageStarted");
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                LogF.i(TAG,"onPageFinished");
                progressbar.setVisibility(View.GONE);
                view.loadUrl("javascript:window.local_method.showHtml('<html>'+" + "document.getElementsByTagName(\"head\")[0].innerHTML+'</html>');");
                checkShowSupportLike();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                LogF.i(TAG,"onReceivedError");
                // TODO Auto-generated method stub
                if (mIsError) {
                    mIsError = false;
                    if (mWebView != null && !TextUtils.isEmpty(url_receive)) {
                        mWebView.loadUrl(url_receive);
                    }
                } else {
                    progressbar.setVisibility(View.INVISIBLE);
                    mEReloadLayout.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.INVISIBLE);
                }
                LogF.d("WebViewActivity", "errorCode =" + errorCode + "\ndescription=" + description);
                super.onReceivedError(view, errorCode, description, failingUrl);

            }

            /**
             * 当接收到https错误时，会回调此函数，在其中可以做错误处理
             */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                LogF.e(TAG,"sslError:"+error.toString());
            }

        });

        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        mEReloadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEReloadLayout.getVisibility() == View.VISIBLE && mWebView != null) {
                    progressbar.setVisibility(View.VISIBLE);
                    mEReloadLayout.setVisibility(View.INVISIBLE);
                    mWebView.loadUrl(url_receive);
                    mWebView.setVisibility(View.VISIBLE);
                }
            }
        });
        mWebView.loadUrl(url_receive);
    }

    @Override
    protected void initLayoutData() {

    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(mToolbar);
        mLeftBack = (LinearLayout) findViewById(R.id.left_back);
        mRightNext = (LinearLayout) findViewById(R.id.next);
        mRightNext.setVisibility(View.GONE);
//        mRightText = (TextView) findViewById(R.id.right_text);
//        mRightIcon = (ImageView) findViewById(R.id.right_image);
//        mRightText.setVisibility(View.GONE);
//        mRightIcon.setVisibility(View.VISIBLE);
//        mRightIcon.setImageResource(R.mipmap.biz_share_btn);
        mToolTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolTitle.setText(mTitleName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 100) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mWebView == null)
                return;
            switch (msg.what) {
                case 0:
                    toast("分享成功");
                    break;
                case 1:
                    toast("分享失败");
                    break;
                case 2:// open publicaccount url with token
                    Map<String, String> extraHeaders = (Map<String, String>) msg.obj;
                    mWebView.loadUrl(url_receive, extraHeaders);
                    break;
                case 3:// open publicaccount url without token
                    mWebView.loadUrl(url_receive);
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null && mWebView.getParent() != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }

    /**
     * @author ryf webview 设置WebChromeClient
     */
    public class WebViewChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int progress) {
            progressbar.setProgress(progress);
            super.onProgressChanged(view, progress);
        }

        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            // Log.i(LOGTAG, "here in on ShowCustomView");
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }

        @Override
        public void onReceivedTitle(WebView view, String webTitle) {
            if (!TextUtils.isEmpty(webTitle) && TextUtils.isEmpty(mTitleName)) {
//                Log.d("VoiceMail", "webTitle : " + webTitle);
//                if (!mIsVM) {
                mToolTitle.setText(webTitle);
//                }
            }
            super.onReceivedTitle(view, webTitle);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            if (mUploadMessage != null) {
                return;
            }
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), 100);
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        // For Android > 4.1.1
        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }

    }

    private void share() {}

    private void checkShowSupportLike() {
        if(Constant.SUPPORT_LIKE.equals(url_type)) {
            mSupportLl.setVisibility(View.VISIBLE);
            mGoodView = new GoodView(this);
            refreshLikeView();
        } else {
            mSupportLl.setVisibility(View.GONE);
        }
    }

    private void clickLike() {
        if(isLike) {
            //取消点赞
            isLike = false;
            cancelLike();
            if(likeNum > 0) {
                likeNum--;
            } else {
                likeNum = 0;
            }
        } else {
            mGoodView.setImage(getResources().getDrawable(R.mipmap.good));
            mGoodView.show(mSupportLl);
            setLike();
            isLike = true;
            likeNum++;
        }
        refreshLikeView();
    }

    private void refreshLikeView() {
        mLikeCount.setText(likeNum + "人觉得有用");
        if(isLike) {
            mLikeView.setImageResource(R.mipmap.bs_like_select_btn);
        } else {
            mLikeView.setImageResource(R.mipmap.bs_like_unselect_btn);
        }
    }

    /**
     * 初始化从服务器获取是否已经点赞
     */
    private void getIsLike() {
        if(token != null && userId != null) {
            String url = ConfigURL.COLL_GET_LIKE + answerId;
            mTupperware.get(Constants.REQUEST_CODE_GET_LIKE, url, this, this, headerparams);
        }
    }

    /**
     * 同步服务器取消点赞
     */
    private void cancelLike() {
        if(token != null && userId != null) {
            String url = ConfigURL.COLL_CANCEL_LIKE + answerId;
            mTupperware.get(Constants.REQUEST_CODE_UNLIKE, url, this, this, headerparams);
        }
    }

    /**
     * 同步服务器点赞
     */
    private void setLike() {
        if(token != null && userId != null) {
            String url = ConfigURL.COLL_SET_LIKE + answerId;
            mTupperware.get(Constants.REQUEST_CODE_LIKE, url, this, this, headerparams);
        }
    }

    @Override
    public void ok(int requestCode, String json) {
        switch (requestCode) {
            case Constants.REQUEST_CODE_GET_LIKE:
                mLikeBean = LikeBean.createInstanceByJson(json);
                if(mLikeBean == null) {
                    toast(getResources().getString(R.string.system_error));
                    return;
                }
                if(mLikeBean.model == 0) {
                    isLike = false;   //取消点赞
                } else {
                    isLike = true;  //点赞
                }
                refreshLikeView();
                break;
            case Constants.REQUEST_CODE_LIKE:
                responseBean = ResponseBean.createInstanceByJson(json);
                if(responseBean == null || !responseBean.success) {
                    toast(getResources().getString(R.string.syn_error));
                    return;
                } else {
                    toast(getResources().getString(R.string.syn_success));
                }
                break;
            case Constants.REQUEST_CODE_UNLIKE:
                responseBean = ResponseBean.createInstanceByJson(json);
                if(responseBean == null || !responseBean.success) {
                    toast(getResources().getString(R.string.syn_error));
                    return;
                } else {
                    toast(getResources().getString(R.string.syn_success));
                }
                break;
        }
    }

    @Override
    public boolean error(int requestCode, JSONObject jsonObject) {
        LogF.e(TAG, "getLike Error" + jsonObject.toString());
        return false;
    }
}
