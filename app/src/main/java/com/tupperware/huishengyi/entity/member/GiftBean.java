package com.tupperware.huishengyi.entity.member;

import com.google.gson.Gson;
import com.tupperware.huishengyi.entity.BaseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dhunter on 2018/6/7.
 */

public class GiftBean extends BaseData {

    public static GiftBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, GiftBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public GiftModel model;
    public List<GiftModel> models;
    public PageInfo pageInfo;

    public static class GiftModel implements Serializable{
        public int gift_order_id; //订单ID
        public String order_sn; //订单编号
        public int order_status; //订单状态值
        public String order_status_name; //订单状态显示名称
        public long create_time; //申请时间
        public long deliver_time; //发送时间
        public long obtain_time; //获取时间
        public String gift_order_province; // 省
        public String gift_order_city; //市
        public String gift_order_district; //区
        public String gift_order_address; //地址
        public String gift_order_tel; //电话
        public String gift_order_consignee; //收件人
        public String product_image; //产品图片路径
        public String gift_order_reason; //取消原因
        public String post_id; //物流单号
        public String logistic_company; //物流公司名称
        public String redundant_product_name_cn; //产品名称
    }

    public class PageInfo {
        public int total;
        public int pages;
        public boolean hasMore;
    }
}
