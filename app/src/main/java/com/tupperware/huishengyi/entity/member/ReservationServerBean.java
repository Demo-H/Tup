package com.tupperware.huishengyi.entity.member;

import com.google.gson.Gson;
import com.tupperware.huishengyi.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/5/31.
 * 从会员详情进入会员活动预约的明细数据属性
 */

public class ReservationServerBean extends BaseData {

    public static ReservationServerBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, ReservationServerBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<ReservationItem> models;

}
