package com.tupperware.biz.entity.login;

import com.google.gson.Gson;
import com.tupperware.biz.entity.BaseData;

import java.io.Serializable;

/**
 * Created by dhunter on 2018/5/16.
 */

public class ForgetPwInfo extends BaseData{
    public InfoModel model;

    public static ForgetPwInfo createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, ForgetPwInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class InfoModel implements Serializable {
        private String employeeId;
        private String employeeCode;
        private String employeeMobile;
        private String employeeName;
        private String employeeGroup;

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getEmployeeCode() {
            return employeeCode;
        }

        public void setEmployeeCode(String employeeCode) {
            this.employeeCode = employeeCode;
        }

        public String getEmployeeMobile() {
            return employeeMobile;
        }

        public void setEmployeeMobile(String employeeMobile) {
            this.employeeMobile = employeeMobile;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public String getEmployeeGroup() {
            return employeeGroup;
        }

        public void setEmployeeGroup(String employeeGroup) {
            this.employeeGroup = employeeGroup;
        }
    }

    public InfoModel getModel() {
        return model;
    }

    public void setModel(InfoModel model) {
        this.model = model;
    }
}
