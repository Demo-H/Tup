package com.android.dhunter.common.config;

import android.os.Environment;

/**
 * Created by dhunter on 2018/2/8.
 */

public class GlobalConfig {
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tupperware/";
    public static final String DEFAULT_TEMP_PATH = "Temp/";

    /**
     * 登录信息
     */

    public static final String APP_FIRST_START = "APP_FIRST_START";
    public static final String LOGIN_TOKEN = "login_token";
    public static final String KEY_DATA_USERID = "userid";
    public static final String EMPLOYEE_GROUP = "employee_group";
    public static final String KEY_DATA_LOGIN_INFO = "login_info";
    public static final String STORE_CODE = "store_code";
    public static final String EMPLOYEE_CODE = "employee_code";
    public static final String STORE_ID = "store_id";
    public static final String PLATFORM = "PC";

    /**
     * appid
     */
    public static final String BUGLY_APP_ID = "7dda9137ea";
    public static final String BUGLY_APP_KEY = "571f100c-5715-4909-8337-0c3c180b9e3b";

    public static final String COOKIE = "cookie";


    public final static String DOWNLOAD_FILE_PATH = "filepath";

    public final static boolean isTest = false;

    /** loadingdialog **/
    public static final String LOADING_TIP = "loading_tip";

    public static String SHARE_PREFERENCE_FILE_NAME = "tup";
    public static final String SHARE_PREFERENCE_SETTING = "tup_setting";

    /**network **/
    public static int HTTP_READ_TIME_OUT = 15;
    public static int HTTP_CONNECT_TIME_OUT = 15;
    public static String BASE_URL = "https://test.tbh.cn/";

    public static boolean DEBUG = true;


}
