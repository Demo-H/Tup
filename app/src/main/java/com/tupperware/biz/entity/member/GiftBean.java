package com.tupperware.biz.entity.member;

import com.google.gson.Gson;
import com.tupperware.biz.entity.BaseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dhunter on 2018/6/7.
 */

public class GiftBean extends BaseData {

    public static GiftBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, GiftBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public GiftInfo model;
    public List<GiftInfo> models;
    public PageInfo pageInfo;

    public static class GiftInfo implements Serializable{
        private int giftOrderId; //订单ID
        private String orderSn; //订单编号
        private int orderStatus; //订单状态值
        private String orderStatusName; //订单状态显示名称
        private long createTime; //申请时间
        private long approveTime; //审核时间
        private long deliverTime; //发送时间
        private long obtainTime; //获取时间
        private long cancelTime; //取消时间
        private String giftOrderProvince; // 省
        private String giftOrderCity; //市
        private String giftOrderDistrict; //区
        private String giftOrderAddress; //地址
        private String giftOrderTel; //电话
        private String giftOrderConsignee; //收件人
        private String productImage; //产品图片路径
        private String giftOrderReason; //取消原因
        private String postId; //物流单号
        private String logisticCompany; //物流公司名称
        private String redundantProductNameCn; //产品名称
        private String redundantProductCode;
        private String redundantCurrentStoreCode;
        private String redundantCurrentStoreName;

        public int getGiftOrderId() {
            return giftOrderId;
        }

        public void setGiftOrderId(int giftOrderId) {
            this.giftOrderId = giftOrderId;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getApproveTime() {
            return approveTime;
        }

        public void setApproveTime(long approveTime) {
            this.approveTime = approveTime;
        }

        public long getDeliverTime() {
            return deliverTime;
        }

        public void setDeliverTime(long deliverTime) {
            this.deliverTime = deliverTime;
        }

        public long getObtainTime() {
            return obtainTime;
        }

        public void setObtainTime(long obtainTime) {
            this.obtainTime = obtainTime;
        }

        public long getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(long cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getGiftOrderProvince() {
            return giftOrderProvince;
        }

        public void setGiftOrderProvince(String giftOrderProvince) {
            this.giftOrderProvince = giftOrderProvince;
        }

        public String getGiftOrderCity() {
            return giftOrderCity;
        }

        public void setGiftOrderCity(String giftOrderCity) {
            this.giftOrderCity = giftOrderCity;
        }

        public String getGiftOrderDistrict() {
            return giftOrderDistrict;
        }

        public void setGiftOrderDistrict(String giftOrderDistrict) {
            this.giftOrderDistrict = giftOrderDistrict;
        }

        public String getGiftOrderAddress() {
            return giftOrderAddress;
        }

        public void setGiftOrderAddress(String giftOrderAddress) {
            this.giftOrderAddress = giftOrderAddress;
        }

        public String getGiftOrderTel() {
            return giftOrderTel;
        }

        public void setGiftOrderTel(String giftOrderTel) {
            this.giftOrderTel = giftOrderTel;
        }

        public String getGiftOrderConsignee() {
            return giftOrderConsignee;
        }

        public void setGiftOrderConsignee(String giftOrderConsignee) {
            this.giftOrderConsignee = giftOrderConsignee;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getGiftOrderReason() {
            return giftOrderReason;
        }

        public void setGiftOrderReason(String giftOrderReason) {
            this.giftOrderReason = giftOrderReason;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getLogisticCompany() {
            return logisticCompany;
        }

        public void setLogisticCompany(String logisticCompany) {
            this.logisticCompany = logisticCompany;
        }

        public String getRedundantProductNameCn() {
            return redundantProductNameCn;
        }

        public void setRedundantProductNameCn(String redundantProductNameCn) {
            this.redundantProductNameCn = redundantProductNameCn;
        }

        public String getRedundantProductCode() {
            return redundantProductCode;
        }

        public void setRedundantProductCode(String redundantProductCode) {
            this.redundantProductCode = redundantProductCode;
        }

        public String getRedundantCurrentStoreCode() {
            return redundantCurrentStoreCode;
        }

        public void setRedundantCurrentStoreCode(String redundantCurrentStoreCode) {
            this.redundantCurrentStoreCode = redundantCurrentStoreCode;
        }

        public String getRedundantCurrentStoreName() {
            return redundantCurrentStoreName;
        }

        public void setRedundantCurrentStoreName(String redundantCurrentStoreName) {
            this.redundantCurrentStoreName = redundantCurrentStoreName;
        }
    }

    public class PageInfo {
        private boolean hasNextPage;
        private List<GiftInfo> list;
        public int total;
        public int pages;
        public boolean hasMore;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public List<GiftInfo> getList() {
            return list;
        }

        public void setList(List<GiftInfo> list) {
            this.list = list;
        }
    }

    public List<GiftInfo> getModels() {
        return models;
    }

    public void setModels(List<GiftInfo> models) {
        this.models = models;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
