package com.tupperware.biz.entity.benefit;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/3/21.
 */

public class CouponResponse extends BaseData {

    private List<ExpenditureContentBean> models;
    private PageInfo pageInfo;

    public class PageInfo {
        private boolean hasNextPage;
        private List<CouponInfo> list;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public List<CouponInfo> getList() {
            return list;
        }

        public void setList(List<CouponInfo> list) {
            this.list = list;
        }
    }

    public static class ExpenditureContentBean {

        private String product_name;
        private long used_time;
        private String imageurl;
        private String member_name;
        private int is_billing;
        private String member_mobile;
        private int integral_amount;
        private int benefit_coupon_id;
        private String coupon_image;
        private int coupon_status;

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public long getUsed_time() {
            return used_time;
        }

        public void setUsed_time(long used_time) {
            this.used_time = used_time;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public int getIs_billing() {
            return is_billing;
        }

        public void setIs_billing(int is_billing) {
            this.is_billing = is_billing;
        }

        public String getMember_mobile() {
            return member_mobile;
        }

        public void setMember_mobile(String member_mobile) {
            this.member_mobile = member_mobile;
        }

        public int getIntegral_amount() {
            return integral_amount;
        }

        public void setIntegral_amount(int integral_amount) {
            this.integral_amount = integral_amount;
        }

        public int getBenefit_coupon_id() {
            return benefit_coupon_id;
        }

        public void setBenefit_coupon_id(int benefit_coupon_id) {
            this.benefit_coupon_id = benefit_coupon_id;
        }

        public String getCoupon_image() {
            return coupon_image;
        }

        public void setCoupon_image(String coupon_image) {
            this.coupon_image = coupon_image;
        }

        public int getCoupon_status() {
            return coupon_status;
        }

        public void setCoupon_status(int coupon_status) {
            this.coupon_status = coupon_status;
        }
    }

    public List<ExpenditureContentBean> getModels() {
        return models;
    }

    public void setModels(List<ExpenditureContentBean> models) {
        this.models = models;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
