package com.tupperware.huishengyi.entity.login;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dhunter on 2018/3/2.
 * 登录返回数据
 */

public class LoginInfo {

    public boolean success;
    public String resultCode;
    public String message;
    public Model model;
    public Extra extra;
    public boolean valid;

    public static LoginInfo createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, LoginInfo.class);
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
    public static class Extra implements Serializable {
        public String token;
        public String type;
        public String employeeGroup;
        public String storeCode;
        public String employeeCode;
        public String platform;
        public String storeEmployeeCode;
        public String storeId;
//        public StoreInfo store_info;  //重点热卖需要传递参数
//        public StoreFromOrgan store_from_organ;  //重点热卖需要传递参数
//        public JSONArray store_info;
//        public JSONArray store_from_organ;
//        private ArrayList<StoreInfo> store_info;
//        private ArrayList<StoreFromOrgan> store_from_organ;

        private ArrayList<StoreInfo> store_info;
        private ArrayList<StoreFromOrgan> store_from_organ;

        public class StoreInfo implements Serializable {
            private String store_id;
            private String store_name;
            private String store_code;
            private String store_phone;
            private String store_status;

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getStore_code() {
                return store_code;
            }

            public void setStore_code(String store_code) {
                this.store_code = store_code;
            }

            public String getStore_phone() {
                return store_phone;
            }

            public void setStore_phone(String store_phone) {
                this.store_phone = store_phone;
            }

            public String getStore_status() {
                return store_status;
            }

            public void setStore_status(String store_status) {
                this.store_status = store_status;
            }
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
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEmployeeGroup() {
            return employeeGroup;
        }

        public void setEmployeeGroup(String employeeGroup) {
            this.employeeGroup = employeeGroup;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getEmployeeCode() {
            return employeeCode;
        }

        public void setEmployeeCode(String employeeCode) {
            this.employeeCode = employeeCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getStoreEmployeeCode() {
            return storeEmployeeCode;
        }

        public void setStoreEmployeeCode(String storeEmployeeCode) {
            this.storeEmployeeCode = storeEmployeeCode;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public ArrayList<StoreInfo> getStore_info() {
            return store_info;
        }

        public void setStore_info(ArrayList<StoreInfo> store_info) {
            this.store_info = store_info;
        }

        public ArrayList<StoreFromOrgan> getStore_from_organ() {
            return store_from_organ;
        }

        public void setStore_from_organ(ArrayList<StoreFromOrgan> store_from_organ) {
            this.store_from_organ = store_from_organ;
        }

        //        public JSONArray getStore_info() {
//            return store_info;
//        }
//
//        public void setStore_info(JSONArray store_info) {
//            this.store_info = store_info;
//        }
//
//        public JSONArray getStore_from_organ() {
//            return store_from_organ;
//        }
//
//        public void setStore_from_organ(JSONArray store_from_organ) {
//            this.store_from_organ = store_from_organ;
//        }
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
