package com.tupperware.huishengyi.entity.saleenter;

import java.io.Serializable;

/**
 * Created by dhunter on 2018/5/29.
 */

public class SaleEnterContent implements Serializable {

    public String code;
    public String name;
    public String url;
    public int saleNum;
    public int stockNum;
    public int localStockNum;
    public int localSaleNum;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getLocalStockNum() {
        return localStockNum;
    }

    public void setLocalStockNum(int localStockNum) {
        this.localStockNum = localStockNum;
    }

    public int getLocalSaleNum() {
        return localSaleNum;
    }

    public void setLocalSaleNum(int localSaleNum) {
        this.localSaleNum = localSaleNum;
    }

}
