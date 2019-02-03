package com.tupperware.biz.entity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/23.
 */

public class ProductEnterBean {
    public int typeId;
    public String typeName;
    public String code;
    public List<ProductEnterContentBean> content;

    public static class ProductEnterContentBean {

        public String goodsName;
        public String date;
        public String goodsNumber;
        public String inventory_day_goods;
        public String dec;
    }
}
