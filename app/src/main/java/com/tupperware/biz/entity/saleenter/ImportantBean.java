package com.tupperware.biz.entity.saleenter;

import com.google.gson.Gson;

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
