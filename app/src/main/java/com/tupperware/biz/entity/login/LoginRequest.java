package com.tupperware.biz.entity.login;

/**
 * Created by dhunter on 2018/7/4.
 */

public class LoginRequest {
//    private String storeEmployeeCode; //username
    private String pwd;
//    private String platform;
    private String clientId;
//    private String password;
    private String platform;
    private int storeEmployeeGroup;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public int getStoreEmployeeGroup() {
        return storeEmployeeGroup;
    }

    public void setStoreEmployeeGroup(int storeEmployeeGroup) {
        this.storeEmployeeGroup = storeEmployeeGroup;
    }
}
