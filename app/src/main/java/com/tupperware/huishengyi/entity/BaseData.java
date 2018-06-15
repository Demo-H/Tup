package com.tupperware.huishengyi.entity;

import com.google.gson.Gson;

/**
 * Created by dhunter on 2018/4/26.
 */

public abstract class BaseData {
    public boolean success;
    public String resultCode;
    public String message;

    public static BaseData createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, BaseData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
