package com.tupperware.huishengyi.entity;

import com.google.gson.Gson;

/**
 * Created by dhunter on 2018/2/9.
 */

public class ResponseHeader {

    private int code;
    private boolean success;
    private String msg;
    private String message;

    public static ResponseHeader createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, ResponseHeader.class);
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
