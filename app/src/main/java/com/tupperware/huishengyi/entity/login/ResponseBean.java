package com.tupperware.huishengyi.entity.login;

import com.google.gson.Gson;

/**
 * Created by dhunter on 2018/4/18.
 */

public class ResponseBean {

    public boolean success;
    public int code;
    public String resultCode;
    public String message;
    public Model model;
    public Extra extra;
    public boolean valid;

    public static ResponseBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, ResponseBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class Model {
        public String pUid;
        public String pName;
        public String passwd;
        public String pCityno;

        public String getpUid() {
            return pUid;
        }

        public void setpUid(String pUid) {
            this.pUid = pUid;
        }

        public String getpName() {
            return pName;
        }

        public void setpName(String pName) {
            this.pName = pName;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getpCityno() {
            return pCityno;
        }

        public void setpCityno(String pCityno) {
            this.pCityno = pCityno;
        }
    }
    public class Extra {
        public String token;
        public String type;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }


}
