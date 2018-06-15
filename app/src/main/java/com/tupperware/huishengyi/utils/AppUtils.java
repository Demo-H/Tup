package com.tupperware.huishengyi.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.tupperware.huishengyi.app.BaseApplication;

import java.util.Locale;

/**
 * Created by dhunter on 2018/1/30.
 */

public class AppUtils {

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersionName() {
        try {
            PackageManager manager = BaseApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取版本versioncode
     *
     * @return 当前应用的versioncode
     */
    public static int getVersionCode() {
        try {
            PackageManager manager = BaseApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            int versioncode = info.versionCode;
            return versioncode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前系统语言
     *
     * @return 当前系统语言
     */
    public static String getLanguage() {
        Locale locale = BaseApplication.getInstance().getResources().getConfiguration().locale;
        String language = locale.getDefault().toString();
        return language;
    }


    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
