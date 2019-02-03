package com.tupperware.biz.utils;

import com.android.dhunter.common.config.GlobalConfig;

/**
 * Created by dhunter on 2018/5/15.
 */

public class ImageUrl {

    public static String parseUrl(String url){
        String[] strings = url.split("://");
        if(strings[0].equalsIgnoreCase("http")){
            return url;
        }
        return GlobalConfig.URL_IMG + url;
    }
}
