package com.tupperware.biz.entity.login;

/**
 * Created by dhunter on 2018/7/6.
 */

public class ResetPwdRequest {
    private String mobile;
    private String code;
    private String employeeId;
    private String employeeGroup;
    private String employeeCode;
    private String newPwd;
    private String againPwd;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeGroup() {
        return employeeGroup;
    }

    public void setEmployeeGroup(String employeeGroup) {
        this.employeeGroup = employeeGroup;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getAgainPwd() {
        return againPwd;
    }

    public void setAgainPwd(String againPwd) {
        this.againPwd = againPwd;
    }
}
