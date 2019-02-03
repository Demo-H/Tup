package com.android.dhunter.common.utils;

/**
 * Created by dhunter on 2018/10/22.
 */

public class StringUtils {

    public static int StringChangeToInt(String string) {
        int a = 0;
        try{
            a = Integer.parseInt(string);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return a;
    }
}
