package com.tupperware.huishengyi.entity.login;

/**
 * Created by dhunter on 2018/7/4.
 */

public class LoginRequest {
    private String storeEmployeeCode; //username
    private String pwd;
    private String platform;

    public String getStoreEmployeeCode() {
        return storeEmployeeCode;
    }

    public void setStoreEmployeeCode(String storeEmployeeCode) {
        this.storeEmployeeCode = storeEmployeeCode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
