package com.tupperware.biz.entity.home;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by dhunter on 2018/4/20.
 * 弃用
 */

public class ToutiaoBean {

    public boolean success;
    public String resultCode;
    public String message;
    public Model model;
    public List<Model> models;
    public int resId;
    public String title;

    public static ToutiaoBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, ToutiaoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class Model {
        public int id;
        public String imgUrl;
        public String link;
        public String subTitle;
        public String title;
    }
}
