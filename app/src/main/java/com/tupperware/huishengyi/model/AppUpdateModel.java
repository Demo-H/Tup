package com.tupperware.huishengyi.model;

import com.google.gson.Gson;

/**
 * Created by dhunter on 2018/3/2.
 */

public class AppUpdateModel {

    public int code;
    public String url;
    public String version;

    public static AppUpdateModel createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, AppUpdateModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
