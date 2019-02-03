package com.tupperware.biz.entity.saleenter;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/5/28.
 */

public class SaleEnterBean extends BaseData {

    public SaleEnterContent model;
    public List<SaleEnterContent> models;
    public PageInfo pageInfo;

//    public static class SaleEnterContent implements Serializable {
//        public String code;
//        public String name;
//        public String url;
//        public int saleNum;
//        public int stockNum;
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public int getSaleNum() {
//            return saleNum;
//        }
//
//        public void setSaleNum(int saleNum) {
//            this.saleNum = saleNum;
//        }
//
//        public int getStockNum() {
//            return stockNum;
//        }
//
//        public void setStockNum(int stockNum) {
//            this.stockNum = stockNum;
//        }
//    }
    public class PageInfo{
        public int pageNum;
        public int pageSize;
    }
}
