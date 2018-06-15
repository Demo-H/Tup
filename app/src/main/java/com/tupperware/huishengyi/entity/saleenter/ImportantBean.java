package com.tupperware.huishengyi.entity.saleenter;

import com.google.gson.Gson;
import com.tupperware.huishengyi.entity.login.LoginInfo;

/**
 * Created by dhunter on 2018/5/30.
 */

public class ImportantBean {
    public int code;
    public String msg;
    public long time;
    public DataUrl data;
    public class DataUrl {
        public String uc_url;
    }

    public static ImportantBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, ImportantBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
