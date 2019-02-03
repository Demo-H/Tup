package com.tupperware.biz.entity;

/**
 * Created by dhunter on 2018/10/24.
 */

public class StaffRequest {
    private long storeEmployeeId;
    private long storeEmployeeEntryTime;
    private int storeEmployeeGender; //0是男,1是女
    private String storeEmployeeMobile;
    private String storeEmployeeName;
    private String storeEmployeePassword;
    private int storeEmployeeStatus; //0、启用，1、未启用

    public long getStoreEmployeeId() {
        return storeEmployeeId;
    }

    public void setStoreEmployeeId(long storeEmployeeId) {
        this.storeEmployeeId = storeEmployeeId;
    }

    public long getStoreEmployeeEntryTime() {
        return storeEmployeeEntryTime;
    }

    public void setStoreEmployeeEntryTime(long storeEmployeeEntryTime) {
        this.storeEmployeeEntryTime = storeEmployeeEntryTime;
    }

    public int getStoreEmployeeGender() {
        return storeEmployeeGender;
    }

    public void setStoreEmployeeGender(int storeEmployeeGender) {
        this.storeEmployeeGender = storeEmployeeGender;
    }

    public String getStoreEmployeeMobile() {
        return storeEmployeeMobile;
    }

    public void setStoreEmployeeMobile(String storeEmployeeMobile) {
        this.storeEmployeeMobile = storeEmployeeMobile;
    }

    public String getStoreEmployeeName() {
        return storeEmployeeName;
    }

    public void setStoreEmployeeName(String storeEmployeeName) {
        this.storeEmployeeName = storeEmployeeName;
    }

    public String getStoreEmployeePassword() {
        return storeEmployeePassword;
    }

    public void setStoreEmployeePassword(String storeEmployeePassword) {
        this.storeEmployeePassword = storeEmployeePassword;
    }

    public int getStoreEmployeeStatus() {
        return storeEmployeeStatus;
    }

    public void setStoreEmployeeStatus(int storeEmployeeStatus) {
        this.storeEmployeeStatus = storeEmployeeStatus;
    }
}
