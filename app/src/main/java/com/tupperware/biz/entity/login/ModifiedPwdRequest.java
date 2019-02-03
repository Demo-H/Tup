package com.tupperware.biz.entity.login;

/**
 * Created by dhunter on 2018/7/6.
 */

public class ModifiedPwdRequest {
    private String oldPassword;
    private String newPassword;
    private int storeEmployeeGroup;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public int getStoreEmployeeGroup() {
        return storeEmployeeGroup;
    }

    public void setStoreEmployeeGroup(int storeEmployeeGroup) {
        this.storeEmployeeGroup = storeEmployeeGroup;
    }
}
