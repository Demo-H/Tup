package com.tupperware.biz.entity.login;

import com.google.gson.Gson;
import com.tupperware.biz.entity.BaseData;

/**
 * Created by dhunter on 2018/4/18.
 */

public class ResponseBean extends BaseData {

    public Model model;

    public static ResponseBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, ResponseBean.class);
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
    public class Extra {
        public String token;
        public String type;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
