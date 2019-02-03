package com.tupperware.biz.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 *
 */
public class VerifyCoupon extends BaseData implements Serializable {

    public CouponModel model;

    public  static VerifyCoupon createInstanceByJson(String json){
        Gson gson = new Gson();
        try {
            return gson.fromJson(json,VerifyCoupon.class);
        }catch (Exception e){
            return null;
        }
    }

    public class CouponModel{
        private String imageurl;//优惠券图片
        private String benefitDesc;//优惠相关描述
        private String benefitFrontName;
        private String benefitCode;

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getBenefitDesc() {
            return benefitDesc;
        }

        public void setBenefitDesc(String benefitDesc) {
            this.benefitDesc = benefitDesc;
        }

        public String getBenefitFrontName() {
            return benefitFrontName;
        }

        public void setBenefitFrontName(String benefitFrontName) {
            this.benefitFrontName = benefitFrontName;
        }

        public String getBenefitCode() {
            return benefitCode;
        }

        public void setBenefitCode(String benefitCode) {
            this.benefitCode = benefitCode;
        }
    }

    public CouponModel getModel() {
        return model;
    }

    public void setModel(CouponModel model) {
        this.model = model;
    }

//    @Override
//    public String toString() {
//        return "VerifyCoupon{" +
//                "success=" + success +
//                ", message='" + message + '\'' +
//                ", image='" + model.image + '\'' +
//                ", description='" + model.description + '\'' +
//                ", product_name='" + model.product_name + '\'' +
//                ", product_code='" + model.product_code + '\'' +
//                ", type='" + model.type + '\'' +
//                '}';
//    }
}
