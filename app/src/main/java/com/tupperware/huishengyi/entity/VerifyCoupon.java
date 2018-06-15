package com.tupperware.huishengyi.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 *
 */
public class VerifyCoupon implements Serializable {

    public boolean success;
    public String message;
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
        private String image;//优惠券图片
        private String description;//优惠相关描述
        private String product_name;
        private String product_code;
        private String type;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_code() {
            return product_code;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CouponModel getModel() {
        return model;
    }

    public void setModel(CouponModel model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "VerifyCoupon{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", image='" + model.image + '\'' +
                ", description='" + model.description + '\'' +
                ", product_name='" + model.product_name + '\'' +
                ", product_code='" + model.product_code + '\'' +
                ", type='" + model.type + '\'' +
                '}';
    }
}
