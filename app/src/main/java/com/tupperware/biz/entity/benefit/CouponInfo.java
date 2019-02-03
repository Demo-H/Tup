package com.tupperware.biz.entity.benefit;

/**
 * Created by dhunter on 2018/11/1.
 */

public class CouponInfo {


    //共用字段
    private String imageurl;
    private long usedTime;
    //以下为优惠券使用字段
    private long availableStartTime;
    private long availableEndTime;
    private String redundantUsedMemberName;
    private String redundantUsedMemberMobile;


    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(long usedTime) {
        this.usedTime = usedTime;
    }

    public long getAvailableStartTime() {
        return availableStartTime;
    }

    public void setAvailableStartTime(long availableStartTime) {
        this.availableStartTime = availableStartTime;
    }

    public long getAvailableEndTime() {
        return availableEndTime;
    }

    public void setAvailableEndTime(long availableEndTime) {
        this.availableEndTime = availableEndTime;
    }

    public String getRedundantUsedMemberName() {
        return redundantUsedMemberName;
    }

    public void setRedundantUsedMemberName(String redundantUsedMemberName) {
        this.redundantUsedMemberName = redundantUsedMemberName;
    }

    public String getRedundantUsedMemberMobile() {
        return redundantUsedMemberMobile;
    }

    public void setRedundantUsedMemberMobile(String redundantUsedMemberMobile) {
        this.redundantUsedMemberMobile = redundantUsedMemberMobile;
    }

//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public String getMemberName() {
//        return memberName;
//    }
//
//    public void setMemberName(String memberName) {
//        this.memberName = memberName;
//    }
//
//    public String getMemberMobile() {
//        return memberMobile;
//    }
//
//    public void setMemberMobile(String memberMobile) {
//        this.memberMobile = memberMobile;
//    }
//
//    public int getIsBilling() {
//        return isBilling;
//    }
//
//    public void setIsBilling(int isBilling) {
//        this.isBilling = isBilling;
//    }
//
//    public int getIntegralAmount() {
//        return integralAmount;
//    }
//
//    public void setIntegralAmount(int integralAmount) {
//        this.integralAmount = integralAmount;
//    }
//
//    public int getBillingRemark() {
//        return billingRemark;
//    }
//
//    public void setBillingRemark(int billingRemark) {
//        this.billingRemark = billingRemark;
//    }
}
