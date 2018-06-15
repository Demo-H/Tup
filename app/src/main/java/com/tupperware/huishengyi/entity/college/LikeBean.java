package com.tupperware.huishengyi.entity.college;

import com.google.gson.Gson;
import com.tupperware.huishengyi.entity.BaseData;

/**
 * Created by dhunter on 2018/5/8.
 */

public class LikeBean extends BaseData {
    public int model;

    public static LikeBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, LikeBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
