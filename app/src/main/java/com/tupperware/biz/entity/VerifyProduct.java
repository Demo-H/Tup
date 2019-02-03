package com.tupperware.biz.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by .
 *
 */
public class VerifyProduct extends BaseData implements Serializable {


    public ProductModel model;

    public static VerifyProduct createInstanceByJson(String json){
        Gson gson = new Gson();
        try {
            return gson.fromJson(json,VerifyProduct.class);
        }catch (Exception e){
            return null;
        }
    }

    public class ProductModel{
        private ProductInfo productInfo;
        public MemberInfo member;

        public class ProductInfo {
            private String productImage;//产品图片
            private String productNameCn; //产品名称
            private float productContentPrice; //产品价格
            private String memberGroupId;  //会员等级属性，MEM:普通会员套 VIP:VIP会员套，不是套装时为空 ,
            private String productDetail; //产品详情


            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public String getProductNameCn() {
                return productNameCn;
            }

            public void setProductNameCn(String productNameCn) {
                this.productNameCn = productNameCn;
            }

            public float getProductContentPrice() {
                return productContentPrice;
            }

            public void setProductContentPrice(float productContentPrice) {
                this.productContentPrice = productContentPrice;
            }

            public String getMemberGroupId() {
                return memberGroupId;
            }

            public void setMemberGroupId(String memberGroupId) {
                this.memberGroupId = memberGroupId;
            }
        }

        public class MemberInfo {
            private Integer memberId;  //会员Id
            private String memberStatus;
            private String memberName;   //会员名称
            private String memberPassword;
            private String salt;
            private String memberGender;  //性别 0男1女
            private String memberMobile;  //会员联系方式
            private String currentStoreId;  // 会员所属专卖店
            private long regTime;  // 注册时间
            private String memberAvatar;   //会员头像
            private String memberBirthday;  // 会员生日 格式1985-08-08
            private int groupId;   // 会员类别 0普通1高级 2粉丝
            private String memberEmail; //邮箱
            private String redundantCurrentStoreStatus;
            private String redundantCurrentStoreCode;
            private String redundantRegisterStoreCode;
            private String redundantUpgradeStoreCode;

            public Integer getMemberId() {
                return memberId;
            }

            public void setMemberId(Integer memberId) {
                this.memberId = memberId;
            }

            public String getMemberStatus() {
                return memberStatus;
            }

            public void setMemberStatus(String memberStatus) {
                this.memberStatus = memberStatus;
            }

            public String getMemberName() {
                return memberName;
            }

            public void setMemberName(String memberName) {
                this.memberName = memberName;
            }

            public String getMemberPassword() {
                return memberPassword;
            }

            public void setMemberPassword(String memberPassword) {
                this.memberPassword = memberPassword;
            }

            public String getSalt() {
                return salt;
            }

            public void setSalt(String salt) {
                this.salt = salt;
            }

            public String getMemberGender() {
                return memberGender;
            }

            public void setMemberGender(String memberGender) {
                this.memberGender = memberGender;
            }

            public String getMemberMobile() {
                return memberMobile;
            }

            public void setMemberMobile(String memberMobile) {
                this.memberMobile = memberMobile;
            }

            public String getCurrentStoreId() {
                return currentStoreId;
            }

            public void setCurrentStoreId(String currentStoreId) {
                this.currentStoreId = currentStoreId;
            }

            public long getRegTime() {
                return regTime;
            }

            public void setRegTime(long regTime) {
                this.regTime = regTime;
            }

            public String getMemberAvatar() {
                return memberAvatar;
            }

            public void setMemberAvatar(String memberAvatar) {
                this.memberAvatar = memberAvatar;
            }

            public String getMemberBirthday() {
                return memberBirthday;
            }

            public void setMemberBirthday(String memberBirthday) {
                this.memberBirthday = memberBirthday;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

            public String getMemberEmail() {
                return memberEmail;
            }

            public void setMemberEmail(String memberEmail) {
                this.memberEmail = memberEmail;
            }

            public String getRedundantCurrentStoreStatus() {
                return redundantCurrentStoreStatus;
            }

            public void setRedundantCurrentStoreStatus(String redundantCurrentStoreStatus) {
                this.redundantCurrentStoreStatus = redundantCurrentStoreStatus;
            }

            public String getRedundantCurrentStoreCode() {
                return redundantCurrentStoreCode;
            }

            public void setRedundantCurrentStoreCode(String redundantCurrentStoreCode) {
                this.redundantCurrentStoreCode = redundantCurrentStoreCode;
            }

            public String getRedundantRegisterStoreCode() {
                return redundantRegisterStoreCode;
            }

            public void setRedundantRegisterStoreCode(String redundantRegisterStoreCode) {
                this.redundantRegisterStoreCode = redundantRegisterStoreCode;
            }

            public String getRedundantUpgradeStoreCode() {
                return redundantUpgradeStoreCode;
            }

            public void setRedundantUpgradeStoreCode(String redundantUpgradeStoreCode) {
                this.redundantUpgradeStoreCode = redundantUpgradeStoreCode;
            }
        }

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(ProductInfo productInfo) {
            this.productInfo = productInfo;
        }

        public MemberInfo getMembers() {
            return member;
        }

        public void setMembers(MemberInfo members) {
            this.member = members;
        }
    }

    public ProductModel getModel() {
        return model;
    }

    public void setModel(ProductModel model) {
        this.model = model;
    }
}
