package com.tupperware.biz.entity.benefit;

/**
 * Created by dhunter on 2018/11/1.
 */

public class BenefitCoinInfo {
    private String imageUrl;
    private long usedTime;
    private String productName;
    private String memberName;
    private String memberMobile;
    private int isBilling;
    private int integralAmount;
    private String billingRemark;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setBillingRemark(String billingRemark) {
        this.billingRemark = billingRemark;
    }

    public long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(long usedTime) {
        this.usedTime = usedTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public int getIsBilling() {
        return isBilling;
    }

    public void setIsBilling(int isBilling) {
        this.isBilling = isBilling;
    }

    public int getIntegralAmount() {
        return integralAmount;
    }

    public void setIntegralAmount(int integralAmount) {
        this.integralAmount = integralAmount;
    }

}
