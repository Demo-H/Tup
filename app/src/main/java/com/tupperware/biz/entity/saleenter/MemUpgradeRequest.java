package com.tupperware.biz.entity.saleenter;

/**
 * Created by dhunter on 2018/7/10.
 */

public class MemUpgradeRequest {
    private String benefitCouponSn;
    private String productUniqueCode;
    private Integer memberId;
    private int isUpgrade;
    private Integer storeId;

    private String qrCode;
    private String uniqueCode;
    private String mobile;
    private String smsCode;
    private String memberName;

    public String getBenefitCouponSn() {
        return benefitCouponSn;
    }

    public void setBenefitCouponSn(String benefitCouponSn) {
        this.benefitCouponSn = benefitCouponSn;
    }

    public String getProductUniqueCode() {
        return productUniqueCode;
    }

    public void setProductUniqueCode(String productUniqueCode) {
        this.productUniqueCode = productUniqueCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getIsUpgrade() {
        return isUpgrade;
    }

    public void setIsUpgrade(int isUpgrade) {
        this.isUpgrade = isUpgrade;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
