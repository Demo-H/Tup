package com.tupperware.biz.entity.login;

import com.google.gson.Gson;
import com.tupperware.biz.entity.BaseData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dhunter on 2018/3/2.
 * 登录返回数据
 */

public class LoginInfo extends BaseData {

    public Extra extra;

    public static LoginInfo createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, LoginInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class Extra implements Serializable {
        public String token;
        public String type;
        public String storeImage;
        public String storeCode;
        public String loginId;
        public int employeeGroup;
        public String employeeCode;
        public Integer storeId;
        public String storeName;
        private String storePhone;
        private String storeAddress;

        private ArrayList<StoreInfo> remaiStoreInfo;
        private ArrayList<StoreFromOrgan> remaiStoreFromOrgan;
//        private StoreInfo remaiStoreInfo;
//        private StoreFromOrgan remaiStoreFromOrgan;

        public class StoreInfo implements Serializable {
            private String store_id;
            private String store_name;
            private String store_code;
            private String store_phone;
            private String store_status;
//            private String storeId;
//            private String storeName;
//            private String storeCode;
//            private String storePhone;
//            private String storeStatus;
        }
        public class StoreFromOrgan implements Serializable {
            private String organ_code;
            private String organ_name;
            private String p_organ_code;
            private String p_organ_name;
            private String p_organ_level;
            private String region_code;
            private String region_name;
            private String province_code;
            private String province_name;
            private String cm_code;
            private String cm_name;
            private String fs_code;
            private String fs_name;
            private String distributor_code;
            private String distributor_name;
//            private String organCode;
//            private String organName;
//            private String pOrganCode;
//            private String pOrganName;
//            private String pOrganLevel;
//            private String regionCode;
//            private String regionName;
//            private String provinceCode;
//            private String provinceName;
//            private String cmCode;
//            private String cmName;
//            private String fsCode;
//            private String fsName;
//            private String distributorCode;
//            private String distributorName;

        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStoreImage() {
            return storeImage;
        }

        public void setStoreImage(String storeImage) {
            this.storeImage = storeImage;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getEmployeeCode() {
            return employeeCode;
        }

        public void setEmployeeCode(String employeeCode) {
            this.employeeCode = employeeCode;
        }

        public int getEmployeeGroup() {
            return employeeGroup;
        }

        public void setEmployeeGroup(int employeeGroup) {
            this.employeeGroup = employeeGroup;
        }

        public Integer getStoreId() {
            return storeId;
        }

        public void setStoreId(Integer storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStorePhone() {
            return storePhone;
        }

        public void setStorePhone(String storePhone) {
            this.storePhone = storePhone;
        }

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public ArrayList<StoreInfo> getRemaiStoreInfo() {
            return remaiStoreInfo;
        }

        public void setRemaiStoreInfo(ArrayList<StoreInfo> remaiStoreInfo) {
            this.remaiStoreInfo = remaiStoreInfo;
        }

        public ArrayList<StoreFromOrgan> getRemaiStoreFromOrgan() {
            return remaiStoreFromOrgan;
        }

        public void setRemaiStoreFromOrgan(ArrayList<StoreFromOrgan> remaiStoreFromOrgan) {
            this.remaiStoreFromOrgan = remaiStoreFromOrgan;
        }
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }
}
