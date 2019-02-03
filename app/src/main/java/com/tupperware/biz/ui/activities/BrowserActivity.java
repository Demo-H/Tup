package com.tupperware.biz.ui.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import com.tupperware.biz.R;
import com.tupperware.biz.base.BaseActivity;
import com.tupperware.biz.config.Constant;
import com.tupperware.biz.utils.logutils.LogF;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/3/9.
 */

public class BrowserActivity extends BaseActivity {

    private static final String TAG = BrowserActivity.class.getSimpleName();

    @BindView(R.id.toolbar_title)
    TextView mToolTitle;
    @BindView(R.id.left_back)
    LinearLayout mLeftBack;
    @BindView(R.id.next)
    LinearLayout mRightNext;
    @BindView(R.id.progressBar)
    ProgressBar progressbar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.reloading_vm)
    RelativeLayout mEReloadLayout; //错误页面提示
    @BindView(R.id.reloadingimg)
    ImageView mEImage;  //错误页面提示图片
    @BindView(R.id.reloadingtx)
    TextView mEText; //错误页面提示文字

    private TextView mRightText;
    private ImageView mRightIcon;
    private boolean mInLoad;
    private boolean mPageStarted;
    private boolean mActivityInPause = true;
    private String mTitleName = "浏览";
    private String url_receive;
    private String url_type;
    private String browserJumpName;
    private ImageButton mRefresh;
    protected boolean mIsError = true; // 网页是否加载错误


    private ValueCallback<Uri> mUploadMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browser_with_toolbar;
    }

    @OnClick({R.id.left_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                onBackPressed();
                break;
//            case R.id.next:
//                toast("share");
//                share();
//                break;
        }

    }


    @Override
    protected void initLayout() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            url_receive = bundle.getString(Constant.OPEN_URL);
            mTitleName = bundle.getString(Constant.URL_TITLE);
            url_type = bundle.getString(Constant.URL_TYPE);
            browserJumpName = bundle.getString(Constant.BROWSER_JUMP_NAME);
        }

        if (!TextUtils.isEmpty(url_receive)) {
            if(url_receive.startsWith("http") || url_receive.startsWith("https")) {
                url_receive = url_receive.trim();
            } else {
                url_receive = "http://" + url_receive.trim();
            }
        }

        WebViewChromeClient webChromeClient = new WebViewChromeClient();
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
        //提高渲染等级,云端加速
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setSupportZoom(true);// 支持缩放
        mWebView.getSettings().setBuiltInZoomControls(true);// 显示放大缩小
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir()
                .getAbsolutePath();
        mWebView.getSettings().setAppCachePath(appCachePath);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        mWebView.addJavascriptInterface(new InJavaScriptInterface(), "local_method");
        /**Android 5.0后默认不支持混合模式，即网页为https，图片为http **/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        initToolBar();

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
                if(progressbar != null) {
                    progressbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                LogF.i(TAG,"onPageFinished");
                if(progressbar != null) {
                    progressbar.setVisibility(View.GONE);
                }
                if(Constant.FMS.equals(browserJumpName)) {
                    view.loadUrl("javascript:window.login('" + userId + "','" + token + "')");
//                    view.loadUrl("javascript:window.login(29444, 111111)");
                }
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
    protected void requestData() {

    }

    private void initToolBar() {
        mRightNext.setVisibility(View.GONE);
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
            if(progressbar != null) {
                progressbar.setProgress(progress);
            }
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


}
