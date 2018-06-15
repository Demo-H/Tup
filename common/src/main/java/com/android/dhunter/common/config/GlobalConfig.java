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


    public static final String COOKIE = "cookie";


    public final static String DOWNLOAD_FILE_PATH = "filepath";

    public final static boolean isTest = false;


}
