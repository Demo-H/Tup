package com.tupperware.huishengyi.utils;

import com.tupperware.huishengyi.network.ConfigURL;

/**
 * Created by dhunter on 2018/5/15.
 */

public class ImageUrl {

    public static String parseUrl(String url){
        String[] strings = url.split("://");
        if(strings[0].equalsIgnoreCase("http")){
            return url;
        }
        return ConfigURL.URL_IMG + url;
    }
}
