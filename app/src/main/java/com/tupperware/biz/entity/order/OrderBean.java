package com.tupperware.biz.entity.order;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/5/16.
 */

public class OrderBean extends BaseData{

    public List<OrderModel> models;
    public PageInfo pageInfo;

    public class OrderModel{
        public long id;
        public String code;
        public String totalMny;
        public String realMny;
        public int goodsNum;
        public long buytime;
        public String buytimeFromNow;
        public GoodModel goods;

        public class GoodModel{
            public String name;
            public String img;
        }
    }

    public class PageInfo{
        public int pageNum;
        public int pageSize;
    }
}
