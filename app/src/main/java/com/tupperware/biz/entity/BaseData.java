package com.tupperware.biz.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by dhunter on 2018/4/26.
 */

public abstract class BaseData {
    public boolean success;
    public String resultCode;
    public String message;
    public boolean valid;
    public List<String> validationMessages;

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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(List<String> validationMessages) {
        this.validationMessages = validationMessages;
    }
}
