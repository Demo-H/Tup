package com.tupperware.huishengyi.network;

/**
 * Created by dhunter on 2018/5/2.
 * 旧接口调用封装
 */

public class TupVolley {} /*implements Response.Listener<String>, Response.ErrorListener {

    private static final String TAG = "TupVolley";
    *//**
     * 网络出错，无法链接网络
     *//*
    public static final int ERROR_CODE_INTERNET = 0;
    *//**
     * 服务器出了问题
     *//*
    public static final int ERROR_CODE_SERVER = 1;
    private static RequestQueue mRequestQueue = RequestManager.getRequestQueue();//请求队列

    private StringRequest mStringRequest;//请求对象
    private int mRequestCode;
    private TupVolleyListener mTupVolleyListener;
    private TupVolleyErrorListener mErrorListener;


    private Context mContext;
    private String url;

    private DefaultRetryPolicy mRetryPolicy;
    private DefaultRetryPolicy mDefaultRetryPolicy;

    public TupVolley(Context context) {
        mContext = context;
        mDefaultRetryPolicy = new DefaultRetryPolicy(2000, 2, 1);
    }

    *//**
     * 使用get 方法请求网络
     *
     * @param requestCode 请求代码，用来区分哪里发来的请求
     * @param url         请求地址
     * @param listener    监听器
     *//*
    public void get(int requestCode, String url, TupVolleyListener listener) {
        get(requestCode, url, listener, null);
    }

    public DefaultRetryPolicy getRetryPolicy() {
        return mRetryPolicy;
    }

    public void setRetryPolicy(DefaultRetryPolicy mRetryPolicy) {
        this.mRetryPolicy = mRetryPolicy;
    }

    public void get(int requestCode, String url, TupVolleyListener listener, TupVolleyErrorListener errorListener) {
        LogF.i(TAG, "Url == " + url);
        //一个实例对最只能发送一个请求

        *//**
         * 增加APP平台增加发送端参数
         *//*
        try{
            Uri uri = Uri.parse(url);
            Uri.Builder builder = uri.buildUpon().appendQueryParameter("platform", "PC");
            this.url = builder.build().toString();

            if (mStringRequest != null && !mStringRequest.isCanceled()) {
                mStringRequest.cancel();
            }
            mTupVolleyListener = listener;
            mErrorListener = errorListener;
            mRequestCode = requestCode;
            mStringRequest = new StringRequest(Request.Method.GET, this.url, this, this);
            mStringRequest.setRetryPolicy(mRetryPolicy == null ? mDefaultRetryPolicy : mRetryPolicy);
            mRequestQueue.add(mStringRequest);
        }catch (Exception e){
            e.printStackTrace();
        }catch (Error error){
            error.printStackTrace();
        }
//        MainApplication.addRequest(mStringRequest, "");
    }

    public void get(int requestCode, String url, TupVolleyListener listener, TupVolleyErrorListener errorListener, final Map<String, String> headerparams) {
        LogF.i(TAG, "Url == " + url);
        //一个实例对最只能发送一个请求

        *//**
         * 增加APP平台增加发送端参数
         *//*
        try{
            Uri uri = Uri.parse(url);
            Uri.Builder builder = uri.buildUpon().appendQueryParameter("platform", "PC");
            this.url = builder.build().toString();

            if (mStringRequest != null && !mStringRequest.isCanceled()) {
                mStringRequest.cancel();
            }
            mTupVolleyListener = listener;
            mErrorListener = errorListener;
            mRequestCode = requestCode;
            mStringRequest = new StringRequest(Request.Method.GET, this.url, this, this) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new LinkedHashMap<>();
                    headers.put("x_request_platform", "PC");
                    headers.put("x_auth_token", headerparams.get("token"));
                    headers.put("x_user_id", headerparams.get("userId"));
                    headers.put("x_employee_group", headerparams.get("employeeGroup"));
                    return headers;
                }
            };
            mStringRequest.setRetryPolicy(mRetryPolicy == null ? mDefaultRetryPolicy : mRetryPolicy);
            mRequestQueue.add(mStringRequest);
        }catch (Exception e){
            e.printStackTrace();
        }catch (Error error){
            error.printStackTrace();
        }
//        MainApplication.addRequest(mStringRequest, "");
    }

    public void post(int requestCode, String url, final Map<String, String> params, TupVolleyListener listener, TupVolleyErrorListener errorListener) {
        LogF.i("tup", "Url == " + url);
        //一个实例最多只能发送一个请求
        if (mStringRequest != null && !mStringRequest.isCanceled()) {
            mStringRequest.isCanceled();
        }
        mTupVolleyListener = listener;
        mErrorListener = errorListener;
        mRequestCode = requestCode;

        *//**
         * 增加APP平台增加发送端参数
         *//*
        params.put("platform", "PC");

        mStringRequest = new StringRequest(Request.Method.POST, url, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");

//                saveCookies(rawCookies);
                return super.parseNetworkResponse(response);
            }
        };
//        mStringRequest.setRetryPolicy(mRetryPolicy == null ? mDefaultRetryPolicy : mRetryPolicy);
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(30*1000,1,1f));
        mRequestQueue.add(mStringRequest);
    }

    *//**
     * 带Header
     * @param requestCode
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     * @param headerparams
     *//*
    public void post(int requestCode, String url, final Map<String, String> params, TupVolleyListener listener, TupVolleyErrorListener errorListener, final Map<String, String> headerparams) {
        LogF.i("tup", "Url == " + url);
        //一个实例最多只能发送一个请求
        if (mStringRequest != null && !mStringRequest.isCanceled()) {
            mStringRequest.isCanceled();
        }
        mTupVolleyListener = listener;
        mErrorListener = errorListener;
        mRequestCode = requestCode;

        *//**
         * 增加APP平台增加发送端参数
         *//*
//        params.put("platform", "PC");
//        params.put("x_request_platform", "PC");
//        params.put("x_auth_token", token);
        mStringRequest = new StringRequest(Request.Method.POST, url, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new LinkedHashMap<>();
                headers.put("x_request_platform", "PC");
                headers.put("x_auth_token", headerparams.get("token"));
                headers.put("x_user_id", headerparams.get("userId"));
                headers.put("x_employee_group", headerparams.get("employeeGroup"));
                return headers;
            }
        };
        mStringRequest.setRetryPolicy(mRetryPolicy == null ? mDefaultRetryPolicy : mRetryPolicy);
        mRequestQueue.add(mStringRequest);
    }


    *//**
     * 取消请求
     *//*
    public void cancel() {
        if (mStringRequest != null && !mStringRequest.isCanceled()) {
//            mStringRequest.cancel();
            BaseApplication.cancelAllRequests("");
        }
    }

    *//**
     * 成功拿到数据
     *
     * @param json
     *//*
    @Override
    public void onResponse(String json) {
        Log.d("Url == onResponse()", json);
        ResponseBean responseBean = ResponseBean.createInstanceByJson(json);
        if (responseBean == null) {
            //返回的数据不是json 格式
            responseBean = new ResponseBean();
            responseBean.setMessage("系统错误");
            if (mErrorListener == null || !mErrorListener.error(mRequestCode, responseBean)) {
                toast("系统错误");
            }
            return;
        }

        if(responseBean.isSuccess()) {
            int resultCode = StringChangeToInt(responseBean.getResultCode());
            switch (resultCode) {
                case StateCode.TOKEN_OUT_DATE:
                    if (mTupVolleyListener != null)
                        mTupVolleyListener.ok(mRequestCode, json);
                    break;
                default:
                    if (mTupVolleyListener != null)
                        mTupVolleyListener.ok(mRequestCode, json);
            }
        } else if(responseBean.getCode() == StateCode.STATE_SUCCESS) {
            if (mTupVolleyListener != null)
                mTupVolleyListener.ok(mRequestCode, json);
        }else {
            if (mErrorListener == null || !mErrorListener.error(mRequestCode, responseBean)) {
                toast(responseBean.getMessage());
            }
        }
        //到了下面就是服务器返回的正常数据
//        switch (responseHeader.getCode()) {
//            case StateCode.STATE_SEARCH_EMPTY:
//            case StateCode.STATE_SUCCESS:
//                if (mTupVolleyListener != null)
//                    mTupVolleyListener.ok(mRequestCode, json);
//                break;
//            case StateCode.STATE_NOT_LOGIN:
//                //没有登录
//                if (mRequestCode!= Constants.REQUEST_CODE_LOGIN) {
//                    ((BaseActivity) mContext).notLogin();
//                    break;
//                }
//            case StateCode.STATE_PARAMETER_INCOMPLETE:
//            case StateCode.STATE_SYSTEM_FAULT:
//            case StateCode.STATE_VERIFY_ERROR:
//            case StateCode.STATE_NAME_NOT_EXIST:
//                if (mErrorListener == null || !mErrorListener.error(mRequestCode, responseHeader)) {
//
//                    if(responseHeader.getMsg() != null && !"".equals(responseHeader.getMsg())) {
//                        toast(responseHeader.getMsg());
//                    } else {
//                        toast(responseHeader.getMessage());
//                        //mTupperwareListener.ok(mRequestCode, json);
//                    }
//                }
//                break;
//            default: //其他错误
//                if (mErrorListener == null || !mErrorListener.error(mRequestCode, responseHeader)) {
//                    toast(responseHeader.getMsg());
//                }
//
//        }
    }

    *//**
     * 请求发生错误
     *
     * @param error
     *//*
    @Override
    public void onErrorResponse(VolleyError error) {

        ResponseBean responseBean = new ResponseBean();
        responseBean.setResultCode(ERROR_CODE_INTERNET + "");
        responseBean.setMessage("系统错误");

        if (error instanceof ServerError) {
            responseBean.setMessage("服务器出错" + error.getMessage());
        } else if (error instanceof NetworkError) {
            responseBean.setMessage("无法连接网络，请检查设置");
        } else if (error instanceof AuthFailureError) {
            responseBean.setMessage("身份验证失败");
        } else if (error instanceof TimeoutError) {
            responseBean.setMessage("访问网络超时，请稍后再试");
        } else {
            responseBean.setMessage("无法连接网络，请检查设置");
        }

        if (mErrorListener == null || !mErrorListener.error(mRequestCode, responseBean)) {
            toast(responseBean.getMessage());
        }
    }

    public interface TupVolleyListener {
        *//**
         * 成功拿到能显示的数据，没错，就是可以显示了，错误的可能性全部处理了
         *
         * @param requestCode 请求代码
         * @param json        拿到的响应对象
         *//*
        public void ok(int requestCode, String json);
    }

    public interface TupVolleyErrorListener {
        *//**
         * 全部错误都来到这里
         *
         * @param requestCode
         * @param errorCode
         * @return 返回true 说明错误事件已经处理
         *//*
        public boolean error(int requestCode, ResponseBean errorCode);
    }


    private void toast(String msg) {
        if (mContext == null) return;
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).toast(msg);
        } else {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private String platformReferer() {
        StringBuilder platSB = new StringBuilder();
        char[] chars = new char[12];

        for(int i = 0 ; i < 5; i ++) {
            Random rd = new Random();

            int rdInt = rd.nextInt(10);
            int result = rdInt + 65;
            if(result % 2 == 0) {
                chars[i] = (char)(result + 32);
            } else {
                chars[i] = (char)result;
            }

            int otherInt = rdInt * 3 + 65;

            if(otherInt % 2 == 0 && otherInt < 90) {
                chars[i + 7] = (char)(otherInt + 32);
            } else if(otherInt <= 90) {
                chars[i + 7] = (char)otherInt;
            } else {
                chars[i + 7] = 'Z';
            }
        }

        chars[5] = 'B';
        chars[6] = 'y';

        return String.valueOf(chars);
    }
}*/
