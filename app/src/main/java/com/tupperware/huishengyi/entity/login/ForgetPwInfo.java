package com.tupperware.huishengyi.entity.login;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by dhunter on 2018/5/16.
 */

public class ForgetPwInfo {
    private boolean success;
    private String msg;
    private String message;
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
        private String employee_id;
        private String employee_code;
        private String employee_mobile;
        private String employee_name;
        private String employee_group;

        public String getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_id(String employee_id) {
            this.employee_id = employee_id;
        }

        public String getEmployee_code() {
            return employee_code;
        }

        public void setEmployee_code(String employee_code) {
            this.employee_code = employee_code;
        }

        public String getEmployee_mobile() {
            return employee_mobile;
        }

        public void setEmployee_mobile(String employee_mobile) {
            this.employee_mobile = employee_mobile;
        }

        public String getEmployee_name() {
            return employee_name;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }

        public String getEmployee_group() {
            return employee_group;
        }

        public void setEmployee_group(String employee_group) {
            this.employee_group = employee_group;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InfoModel getModel() {
        return model;
    }

    public void setModel(InfoModel model) {
        this.model = model;
    }
}
