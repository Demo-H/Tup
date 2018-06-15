package com.tupperware.huishengyi.entity.order;

import com.tupperware.huishengyi.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/5/17.
 * 订单详情
 */

public class OrderItemBean extends BaseData {

    public OrderItemModel model;

    public class OrderItemModel{
        public long id;
        public String recName;
        public String recMobile;
        public String recAddress;
        public String code;
        public String takeTypeValue;
        public int orderType;
        public String orderTypeValue;
        public String remark;
        public String totalMny;
        public String realMny;
        public String postage;
        public String orderStatus;
        public long buytime;
        public List<GoodsModel> goodsList;
        public class GoodsModel{
            public String goodsType;
            public String name;
            public String img;
            public int num;
            public String totalMny;
            public String price;
            public String typeCode;
        }
    }

}
