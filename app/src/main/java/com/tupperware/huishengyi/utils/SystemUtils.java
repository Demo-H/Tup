package com.tupperware.huishengyi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dhunter on 2018/3/21.
 */

public class SystemUtils {

    public static String getCurrentDate() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
