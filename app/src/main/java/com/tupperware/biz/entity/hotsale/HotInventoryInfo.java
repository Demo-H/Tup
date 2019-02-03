package com.tupperware.biz.entity.hotsale;

/**
 * Created by dhunter on 2018/11/30.
 */

public class HotInventoryInfo{

    private int id;
    private String goodsName;
    private boolean status;  //1 正常 0 隐藏 ,
    private long timeEnd;
    private long timeStart;
    private String description;  //备注 ,
    private String explain;  //产品说明 ,
    private int acAdminId;
    private String goodsSn;
    private int originalStock;  //期初库存
    private long stockUpdateTime;  //最新更新时间 ,
    private int xsl;  //累计销售量
    private int jhl;  //累计进货量 ,
    private int monthJhl; //当月进货量 ,
    private int monthXsl;  //当月销售量 ,
    private int dayJhl; //当天进货量 ,
    private int dayXsl;  //当天销售量 ,

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getAcAdminId() {
        return acAdminId;
    }

    public void setAcAdminId(int acAdminId) {
        this.acAdminId = acAdminId;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public int getOriginalStock() {
        return originalStock;
    }

    public void setOriginalStock(int originalStock) {
        this.originalStock = originalStock;
    }

    public long getStockUpdateTime() {
        return stockUpdateTime;
    }

    public void setStockUpdateTime(long stockUpdateTime) {
        this.stockUpdateTime = stockUpdateTime;
    }

    public int getXsl() {
        return xsl;
    }

    public void setXsl(int xsl) {
        this.xsl = xsl;
    }

    public int getJhl() {
        return jhl;
    }

    public void setJhl(int jhl) {
        this.jhl = jhl;
    }

    public int getMonthJhl() {
        return monthJhl;
    }

    public void setMonthJhl(int monthJhl) {
        this.monthJhl = monthJhl;
    }

    public int getMonthXsl() {
        return monthXsl;
    }

    public void setMonthXsl(int monthXsl) {
        this.monthXsl = monthXsl;
    }

    public int getDayJhl() {
        return dayJhl;
    }

    public void setDayJhl(int dayJhl) {
        this.dayJhl = dayJhl;
    }

    public int getDayXsl() {
        return dayXsl;
    }

    public void setDayXsl(int dayXsl) {
        this.dayXsl = dayXsl;
    }
}
