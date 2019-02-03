package com.tupperware.biz.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerBean extends BaseData {

    private List<StaffContentBean> models;

    public static class StaffContentBean implements Serializable {
        private long storeEmployeeId;
        private int storeEmployeeStatus;   //false表示已启用，ture表示停用，0表示启用，1表示停用，服务器返回的时候0取了false，1取了true
        private String storeEmployeeName;
        private String storeEmployeePassword;
        private int storeEmployeeGroup;
        private String storeEmployeeMobile;
        private String storeEmployeeCode;
        private int storeEmployeeGender;
        private long storeEmployeeEntryTime;

        public long getStoreEmployeeId() {
            return storeEmployeeId;
        }

        public void setStoreEmployeeId(long storeEmployeeId) {
            this.storeEmployeeId = storeEmployeeId;
        }

        public int getStoreEmployeeStatus() {
            return storeEmployeeStatus;
        }

        public void setStoreEmployeeStatus(int storeEmployeeStatus) {
            this.storeEmployeeStatus = storeEmployeeStatus;
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

        public int getStoreEmployeeGroup() {
            return storeEmployeeGroup;
        }

        public void setStoreEmployeeGroup(int storeEmployeeGroup) {
            this.storeEmployeeGroup = storeEmployeeGroup;
        }

        public String getStoreEmployeeMobile() {
            return storeEmployeeMobile;
        }

        public void setStoreEmployeeMobile(String storeEmployeeMobile) {
            this.storeEmployeeMobile = storeEmployeeMobile;
        }

        public String getStoreEmployeeCode() {
            return storeEmployeeCode;
        }

        public void setStoreEmployeeCode(String storeEmployeeCode) {
            this.storeEmployeeCode = storeEmployeeCode;
        }

        public int getStoreEmployeeGender() {
            return storeEmployeeGender;
        }

        public void setStoreEmployeeGender(int storeEmployeeGender) {
            this.storeEmployeeGender = storeEmployeeGender;
        }

        public long getStoreEmployeeEntryTime() {
            return storeEmployeeEntryTime;
        }

        public void setStoreEmployeeEntryTime(long storeEmployeeEntryTime) {
            this.storeEmployeeEntryTime = storeEmployeeEntryTime;
        }
    }

    public List<StaffContentBean> getModels() {
        return models;
    }

    public void setModels(List<StaffContentBean> models) {
        this.models = models;
    }
}
