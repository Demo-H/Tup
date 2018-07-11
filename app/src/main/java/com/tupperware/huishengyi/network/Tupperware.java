package com.tupperware.huishengyi.network;

/**
 * Created by dhunter on 2018/2/2.
 */

public class Tupperware {

}/*implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String TAG = "Tupperware";
    *//**
     * 网络出错，无法链接网络
     *//*
    public static final int ERROR_CODE_INTERNET = 0;
    *//**
     * 服务器出了问题
     *//*
    public static final int ERROR_CODE_SERVER = 1;

    private static RequestQueue mRequestQueue = RequestManager.getRequestQueue();//请求队列
//    private StringRequest mStringRequest;//请求对象
    private JsonObjectRequest jsonObjectRequest;
    private int mRequestCode;
    private TupperwareListener mTupperwareListener;
    private TupperwareErrorListener mErrorListener;
    private Context mContext;
    private String url;

    private DefaultRetryPolicy mRetryPolicy;
    private DefaultRetryPolicy mDefaultRetryPolicy;

    public Tupperware(Context context) {
        mContext = context;
        mDefaultRetryPolicy = new DefaultRetryPolicy(10000, 2, 1); //21s
    }

    *//**
     * 使用get 方法请求网络
     *
     * @param requestCode 请求代码，用来区分哪里发来的请求
     * @param url         请求地址
     * @param listener    监听器
     *//*
    public void get(int requestCode, String url, TupperwareListener listener) {
        get(requestCode, url, listener, null);
    }

    public DefaultRetryPolicy getRetryPolicy() {
        return mRetryPolicy;
    }

    public void setRetryPolicy(DefaultRetryPolicy mRetryPolicy) {
        this.mRetryPolicy = mRetryPolicy;
    }

    public void get(int requestCode, String url, TupperwareListener l, TupperwareErrorListener errorListener) {
            Log.i(TAG, "get()--Url == " + url);
        //一个实例对最只能发送一个请求

        *//**
         * 增加APP平台增加发送端参数
         *//*
        try{
            Uri uri = Uri.parse(url);
            Uri.Builder builder = uri.buildUpon().appendQueryParameter("platform_referer", platformReferer());
            this.url = builder.build().toString();

            if (jsonObjectRequest != null && !jsonObjectRequest.isCanceled()) {
                jsonObjectRequest.isCanceled();
            }
            mTupperwareListener = l;
            mErrorListener = errorListener;
            mRequestCode = requestCode;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            jsonObjectRequest.setRetryPolicy(mRetryPolicy == null ? mDefaultRetryPolicy : mRetryPolicy);
            mRequestQueue.add(jsonObjectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }catch (Error error){
            error.printStackTrace();
        }
//        MainApplication.addRequest(mStringRequest, "");
    }

    *//**
     * 带Header的头
     * @param requestCode
     * @param url
     * @param l
     * @param errorListener
     * @param headerparams
     *//*
    public void get(int requestCode, String url, TupperwareListener l, TupperwareErrorListener errorListener,  final Map<String, String> headerparams) {
        Log.i(TAG, "get()--Url == " + url);
        //一个实例对最只能发送一个请求

        *//**
         * 增加APP平台增加发送端参数
         *//*
        try{
//            Uri uri = Uri.parse(url);
//            Uri.Builder builder = uri.buildUpon().appendQueryParameter("platform_referer", platformReferer());
//            this.url = builder.build().toString();

            if (jsonObjectRequest != null && !jsonObjectRequest.isCanceled()) {
                jsonObjectRequest.isCanceled();
            }
            mTupperwareListener = l;
            mErrorListener = errorListener;
            mRequestCode = requestCode;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this){
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
            jsonObjectRequest.setRetryPolicy(mRetryPolicy == null ? mDefaultRetryPolicy : mRetryPolicy);
            mRequestQueue.add(jsonObjectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }catch (Error error){
            error.printStackTrace();
        }
//        MainApplication.addRequest(mStringRequest, "");
    }

    *//**
     * 不带Header
     * @param requestCode
     * @param url
     * @param params
     * @param l
     * @param errorListener
     *//*
    public void post(int requestCode, String url, final Map<String, String> params, TupperwareListener l, TupperwareErrorListener errorListener) {
            Log.i(TAG, "post()--Url == " + url);


        //一个实例最多只能发送一个请求
        if (jsonObjectRequest != null && !jsonObjectRequest.isCanceled()) {
            jsonObjectRequest.isCanceled();
        }
        mTupperwareListener = l;
        mErrorListener = errorListener;
        mRequestCode = requestCode;

        *//**
         * 增加APP平台增加发送端参数
         *//*
        params.put("platform_referer", platformReferer());

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), this, this);
        jsonObjectRequest.setRetryPolicy(mRetryPolicy == null ? mDefaultRetryPolicy : mRetryPolicy);
        mRequestQueue.add(jsonObjectRequest);


//        mStringRequest = new StringRequest(Request.Method.POST, url, this, this) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                return params;
//            }
//        };
//        mStringRequest.setRetryPolicy(mRetryPolicy == null ? mDefaultRetryPolicy : mRetryPolicy);

//        MainApplication.addRequest(mStringRequest, "");
//        mRequestQueue.add(mStringRequest);
    }

    *//**
     * 带Header
     * @param requestCode
     * @param url
     * @param params
     * @param l
     * @param errorListener
     * @param headerparams
     *//*
    public void post(int requestCode, String url, final Map<String, String> params, TupperwareListener l, TupperwareErrorListener errorListener, final Map<String, String> headerparams) {
            Log.i(TAG, "post()--Url == " + url);
        //一个实例最多只能发送一个请求

        if (jsonObjectRequest != null && !jsonObjectRequest.isCanceled()) {
            jsonObjectRequest.isCanceled();
        }
        mTupperwareListener = l;
        mErrorListener = errorListener;
        mRequestCode = requestCode;

        *//**
         * 增加APP平台增加发送端参数
         *//*
        params.put("platform_referer", platformReferer());
//        params.put(name, name);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), this, this) {
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
        jsonObjectRequest.setRetryPolicy(mRetryPolicy == null ? mDefaultRetryPolicy : mRetryPolicy);
        mRequestQueue.add(jsonObjectRequest);

    }



    *//*protected HttpURLConnection createConnection(URL url)
            throws IOException {
        return (HttpURLConnection)url.openConnection();
    }

    private HttpURLConnection openConnection(URL url, Request<?> request)
            throws IOException {
        HttpURLConnection connection = createConnection(url);

        int timeoutMs = request.getTimeoutMs();
        connection.setConnectTimeout(timeoutMs);
        connection.setReadTimeout(timeoutMs);
        connection.setUseCaches(false);
        connection.setDoInput(true);

        return connection;
    }*//*
    *//**
     * 取消请求
     *//*
    public void cancel() {
        if (jsonObjectRequest != null && !jsonObjectRequest.isCanceled()) {
//            mStringRequest.cancel();
            BaseApplication.cancelAllRequests("");
        }
    }

    *//**
     * 成功拿到数据
     *
     * @param jsonObject
     *//*
    @Override
    public void onResponse(JSONObject jsonObject) {

        Log.d("Url == onResponse()", jsonObject.toString());
        try {
            if(jsonObject == null) {
                jsonObject = new JSONObject();
                jsonObject.put("message", "系统异常");
                if (mErrorListener == null || !mErrorListener.error(mRequestCode, jsonObject)) {
                    toast("系统异常");
                }
                return;
            }

            if(jsonObject.getBoolean("success")) {
                int resultCode = StringUtils.StringChangeToInt(jsonObject.getString("resultCode"));
                switch (resultCode) {
                    case StateCode.TOKEN_OUT_DATE:
                        if (mTupperwareListener != null)
                            mTupperwareListener.ok(mRequestCode, jsonObject.toString());
                        break;
                    default:
                        if (mTupperwareListener != null)
                            mTupperwareListener.ok(mRequestCode, jsonObject.toString());
                }
            } else {
                if (mErrorListener == null || !mErrorListener.error(mRequestCode, jsonObject)) {
                    toast(jsonObject.getString("message"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
//    @Override
//    public void onResponse(String json) {
//
//        if (GlobalConfig.isTest)
//            startUrlTest(url, json);
//
//        Log.d("Url == onResponse()", json);
//
//        ResponseHeader responseHeader = ResponseHeader.createInstanceByJson(json);
//        if (responseHeader == null) {
//            //返回的数据不是json 格式
//            responseHeader = new ResponseHeader();
//            responseHeader.setCode(ERROR_CODE_INTERNET);
//            responseHeader.setMsg("系统错误");
//            if (mErrorListener == null || !mErrorListener.error(mRequestCode, responseHeader)) {
//                toast("系统错误");
//            }
//            return;
//        }
//        //到了下面就是服务器返回的正常数据
//        switch (responseHeader.getCode()) {
//            case StateCode.STATE_SEARCH_EMPTY:
//            case StateCode.STATE_SUCCESS:
//                if (mTupperwareListener != null)
//                    mTupperwareListener.ok(mRequestCode, json);
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
//    }

    *//**
     * 请求发生错误
     *
     * @param error
     *//*
    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultCode", ERROR_CODE_INTERNET);
            jsonObject.put("message", "系统错误");

            if (error instanceof ServerError) {
                jsonObject.put("message","服务器出错" + error.getMessage());
            } else if (error instanceof NetworkError) {
                jsonObject.put("message","无法连接网络，请检查设置");
            } else if (error instanceof AuthFailureError) {
                jsonObject.put("message","身份验证失败");
            } else if (error instanceof TimeoutError) {
                jsonObject.put("message","访问网络超时，请稍后再试");
            } else {
                jsonObject.put("message","无法连接网络，请检查设置");
            }

            if (mErrorListener == null || !mErrorListener.error(mRequestCode, jsonObject)) {
                toast(jsonObject.getString("message"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface TupperwareListener {
        *//**
         * 成功拿到能显示的数据，没错，就是可以显示了，错误的可能性全部处理了
         *
         * @param requestCode 请求代码
         * @param json        拿到的响应对象
         *//*
        public void ok(int requestCode, String json);
    }

    public interface TupperwareErrorListener {
        *//**
         * 全部错误都来到这里
         *
         * @param requestCode
         * @param jsonObject
         * @return 返回true 说明错误事件已经处理
         *//*
        public boolean error(int requestCode, JSONObject jsonObject);
    }


    private void toast(String msg) {
        if (mContext == null) return;
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).toast(msg);
        } else {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void startUrlTest(String url, String json) {
        Intent intent = new Intent(mContext, URLTestActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("response", json);
        mContext.startActivity(intent);
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
