package com.tupperware.huishengyi.entity.saleenter;

import java.util.List;

/**
 * Created by dhunter on 2018/6/5.
 */

public class PostEnterBean {
    public String date;
    public List<EnterRecords> records;
    public String storeCode;

    public static class EnterRecords {
        public String code;
        public String name;
        public int saleNum;
        public int stockNum;
        public String url;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(int saleNum) {
            this.saleNum = saleNum;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<EnterRecords> getRecords() {
        return records;
    }

    public void setRecords(List<EnterRecords> records) {
        this.records = records;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
