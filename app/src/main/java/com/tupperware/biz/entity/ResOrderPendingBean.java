package com.tupperware.biz.entity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/15.
 */

public class ResOrderPendingBean {

    public int typeId;
    public String typeName;
    public String code;
    public List<OrderContentBean> content;

    public static class OrderContentBean {

        public String orderNumber;
        public String goodsName;
        public String goodsimageUrl;
        public String orderTime;
        public String endArticleTime;
        public String goodsDes;
        public String goodsunitPrice;
        public int goodsNumber;
        public String SumbitPrice;
        public int image;
    }

}
