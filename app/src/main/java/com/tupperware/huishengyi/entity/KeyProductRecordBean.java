package com.tupperware.huishengyi.entity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/22.
 */

public class KeyProductRecordBean {
    public int typeId;
    public String typeName;
    public String code;
    public List<KeyProductRecordContentBean> content;

    public static class KeyProductRecordContentBean {

        public String goodsName;
        public String date;
        public String sold_in_goods;
        public String sold_out_goods;
        public String sold_origin_goods;

        public String inventory_active_in_goods;
        public String inventory_active_out_goods;
        public String inventory_active_origin_goods;
        public String inventory_day_in_goods;
        public String inventory_day_out_goods;
        public String inventory_active_in_goods_percent;
        public String sales_cycle;
        public String dec;
    }
}
