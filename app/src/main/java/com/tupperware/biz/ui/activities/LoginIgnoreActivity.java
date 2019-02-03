package com.tupperware.biz.ui.activities;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by dhunter on 2018/2/8.
 * 不继承BaseActivity，防止DataManager过早初始化，无法set Header
 */

public class LoginIgnoreActivity extends RxAppCompatActivity /*implements View.OnClickListener,
        TupVolley.TupVolleyListener, TupVolley.TupVolleyErrorListener*/ {

    /*private static final String TAG = "LoginIgnoreActivity";
    private EditText mUserNameText;
    private EditText mPwText;
    private Button mLoginButton;
    private TextView mForgetPwText;
    private SharePreferenceData mSharePreDate;
//    public Tupperware mTupperware = new Tupperware(this);
    public TupVolley mTupVolley = new TupVolley(this);
    protected Unbinder mUnbinder;
    private Toast toast = null;
    private LoadingDialogFragment dialogFragment;

    private String mUserName;
    private String mPw;
    private int default_min_password_length = 6;
    private LoginInfo mLoginInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSharePreDate = new SharePreferenceData(getApplicationContext());
        initLayout();
        mLoginButton.setOnClickListener(this);
        mForgetPwText.setOnClickListener(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
    }

    protected void initLayout() {
        mUserNameText = (EditText) findViewById(R.id.user_name);
        mPwText = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.login);
        mForgetPwText = (TextView) findViewById(R.id.forget_password);
        mUserName = (String) mSharePreDate.getParam(GlobalConfig.KEY_DATA_USERID, "");
        if(mUserName != null && !mUserName.isEmpty()) {
            mUserNameText.setText(mUserName);
            mPwText.requestFocus();
        }
        setPermission();

        mUserNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPwText.setText("");
            }
        });
        mPwText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >= default_min_password_length) {
                    mLoginButton.setEnabled(true);
                } else {
                    mLoginButton.setEnabled(false);
                }
            }
        });
//        if(Constant.DemoTest) {
//            mLoginButton.setEnabled(true);
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if(Constant.Demo) {
                    Intent intent = new Intent(LoginIgnoreActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                if(Constant.DemoF) {
                    Intent intent = new Intent(LoginIgnoreActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    try{
                        if(Build.VERSION.SDK_INT>=23) {
                            if (ContextCompat.checkSelfPermission(BaseApplication.getInstance(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                // 申请权限
                                Toast.makeText(v.getContext(), "请打开文件使用权限", Toast.LENGTH_SHORT).show();
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_WRITE_EXTERNAL_STORAGE);
                                return;
                            }
                        }
                        startLogin();
                    } catch (Exception e) {
                        if(Build.VERSION.SDK_INT>=23) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_WRITE_EXTERNAL_STORAGE);
                            return;
                        }
                    }
                }
                break;
            case R.id.forget_password:
                startToForgetPwActivity();
                break;
        }
    }

    @Override
    public void ok(int requestCode, String json) {
        Log.i(TAG,"ok()");
        // need add
        hideDialog();
        MemberFiltrateCondition.getInstance().reset();

        mLoginInfo = LoginInfo.createInstanceByJson(json);
        if(mLoginInfo == null) {
            toast(getResources().getString(R.string.data_empty));
            return;
        }
//        mSharePreDate.setParam(Constants.KEY_DATA_TOKEN, mLoginInfo.getExtra().getToken());
//        mSharePreDate.setParam(Constants.KEY_DATA_USERNAME, mLoginInfo.getModel().getpUid());

        String token = mLoginInfo.getExtra().getToken();
        String userId = mLoginInfo.getModel().getpUid();
        String storeCode = mLoginInfo.getExtra().getStoreCode();
        String employeeGroup = mLoginInfo.getExtra().getEmployeeGroup();
        String employeeCode = mLoginInfo.getExtra().getEmployeeCode();
        String stordId = mLoginInfo.getExtra().getStoreId();
        mSharePreDate.setParam(GlobalConfig.LOGIN_TOKEN, token);
        mSharePreDate.setParam(GlobalConfig.KEY_DATA_USERID, userId);
        mSharePreDate.setParam(GlobalConfig.STORE_CODE, storeCode);  //店编
        mSharePreDate.setParam(GlobalConfig.EMPLOYEE_CODE, employeeCode);
        mSharePreDate.setParam(GlobalConfig.STORE_ID, stordId);
        if(employeeGroup != null) {
            mSharePreDate.setParam(GlobalConfig.EMPLOYEE_GROUP, employeeGroup);
        } else {
            mSharePreDate.setParam(GlobalConfig.EMPLOYEE_GROUP, "0");
        }
        mSharePreDate.setParam(GlobalConfig.KEY_DATA_LOGIN_INFO, json);
//        mSharePreDate.setParam(GlobalConfig.KEY_DATA_LOGIN_INFO, ObjectUtil.jsonFormatter(mLoginInfo));
//        mDataManager.saveSPData(GlobalConfig.LOGIN_TOKEN, mLoginInfo.getExtra().getToken());
//        mDataManager.saveSPData(GlobalConfig.KEY_DATA_USERID, mLoginInfo.getModel().getpUid());
//        if(mLoginInfo.getEmployee_group() == mLoginInfo.EMPLOYEE_GROUP_BOSS){
//            mSharePreDate.setParam(Constants.KEY_DATA_BOSS, Constants.KEY_DATA_BOSS);
//        }else{
//            mSharePreDate.remove(Constants.KEY_DATA_BOSS);
//        }

        Intent intent = new Intent(LoginIgnoreActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

*//*    @Override
    public boolean error(int requestCode, ResponseBean responseBean) {
        Log.i(TAG,"login errorCode = " + responseBean.getMessage());
        hideDialog();
        return false;
    }*//*

    @Override
    public boolean error(int requestCode, ResponseBean errorCode) {
        Log.i(TAG,"login errorCode = " + errorCode.getMessage());
        hideDialog();
        return false;
    }

    private void startLogin() {
        mUserName = mUserNameText.getText().toString().trim();
        if (mUserName.isEmpty()) {
            toast(getResources().getString(R.string.user_name_empty));
            return;
        }
        mPw = mPwText.getText().toString().trim();
        if (mPw.isEmpty()) {
            toast(getResources().getString(R.string.password_empty));
            return;
        }
        showDialog();

        *//*Map<String, String> params = new HashMap<>();
        if(Constant.Demo) {
            params.put("clientId", "29444");   // 测试用户名29444
            params.put("password", "111111");  // 测试密码111111
            params.put("platform", "PC");  //测试平台
        } else {
            params.put("clientId", mUserName);   // 测试用户名29444
            params.put("password", mPw);  // 测试密码111111
            params.put("platform", "PC");  //测试平台
        }
        mTupperware.post(Constants.REQUEST_CODE_LOGIN, ConfigURL.LOGIN, params, this, this);*//*

        Map<String, String> params = new HashMap<>();
        params.put("storeEmployeeCode", mUserName);
        params.put("pwd", mPw);
        params.put("channelId", "");

        mTupVolley.post(Constants.REQUEST_CODE_LOGIN, ServerURL.LOGIN, params, this, this);


//        Uri uri = Uri.parse(ServerURL.LOGIN);
//        uri.buildUpon();
//        Uri.Builder builder = uri.buildUpon();
//        builder.appendQueryParameter("store_name", mUserName);
//        builder.appendQueryParameter("pwd", mPw);
//        String channelId = (String) mSharePreDate.getParam(Constants.KEY_DATA_CHANNEL_ID,"");
//        builder.appendQueryParameter("channel_id",channelId);
//        String url = builder.build().toString();
//        showDialog();
//        mTupperware.get(Constants.REQUEST_CODE_LOGIN, url, LoginIgnoreActivity.this, LoginIgnoreActivity.this);
    }

    private void startToForgetPwActivity() {
        Intent intent = new Intent(LoginIgnoreActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    *//**
     * 添加点击非EditText 部分隐藏InputMethod
     *
     * @param ev
     * @return
     *//*
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (AppUtils.isShouldHideInput(mUserNameText, ev) && AppUtils.isShouldHideInput(mPwText, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(mUserNameText.getWindowToken(), 0);
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mTupperware.cancel();
//        mTupperware = null;
        mTupVolley.cancel();
        mTupVolley = null;
        if(mUnbinder != null)
            mUnbinder.unbind();
    }

    public void hideDialog() {
        if (dialogFragment != null) {
            try {
                dialogFragment.dismiss();
            } catch (Exception e) {
            }
            dialogFragment = null;
        }
    }

    public void showDialog() {
        hideDialog();
        try { //防止在Activity 还没有恢复状态就showDialog
            dialogFragment = LoadingDialogFragment.newInstance(getResources().getString(R.string.loading));
            dialogFragment.show(getSupportFragmentManager(), null);
        } catch (Exception e) {
            dialogFragment = null;
        }
    }

    public void toast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    // Android 获取相关权限
    private void setPermission() {
        try{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQ_PERM_CAMERA);
                return;
            }
        } catch (Exception e) {
            toast("请打开APP所需所有权限,否则部分功能会出现无法使用的现象");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }*/
}
