package com.tupperware.huishengyi.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by .
 *
 */
public class VerifyProduct implements Serializable {

    public boolean success;
    public String message;
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
        public String cant_be_used_reason;//产品不可使用原因
        public String product_image;//产品图片
        public String product_name;//产品名称
        public double unit_price;//产品价格
        public boolean coupon_can_be_used;//产品是否可用
        public String product_group; // MEM:普通会员套 VIP:VIP会员套 （不是套装是为 false）
        public MemberInfo members;

        public class MemberInfo {
            private int member_id;  //会员Id
            private String member_status;
            private String member_name;   //会员名称
            private String member_password;
            private String salt;
            private String member_gender;  //性别 0男1女
            private String member_mobile;  //会员联系方式
            private String current_store_id;  // 会员所属专卖店
            private long reg_time;  // 注册时间
            private String member_avatar;   //会员头像
            private String member_birthday;  // 会员生日 格式1985-08-08
            private int group_id;   // 会员类别 0普通1高级 2粉丝
            private String member_email; //邮箱
            private String redundant_current_store_status;
            private String redundant_current_store_code;
            private String redundant_register_store_code;
            private String redundant_upgrade_store_code;
            private String register_product_code;  // is_fans
            private String upgrade_product_code;  // 升级产品唯一码
            private long upgrade_time;  // 升级时间
            private String store_image; //门店图像
            private int register_channel;   //注册来源；注册渠道 1微信 2 web 3 IOS 4 安卓 5短信
            private String member_remark;  // 会员备注

//            private String member_address;  //会员地址
//            private int integral_amount;  // 会员积分
//            private String register_store;  //会员入会专卖店
//            private int member_coupon_total; //专享礼券数


            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public String getMember_status() {
                return member_status;
            }

            public void setMember_status(String member_status) {
                this.member_status = member_status;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getMember_password() {
                return member_password;
            }

            public void setMember_password(String member_password) {
                this.member_password = member_password;
            }

            public String getSalt() {
                return salt;
            }

            public void setSalt(String salt) {
                this.salt = salt;
            }

            public String getMember_gender() {
                return member_gender;
            }

            public void setMember_gender(String member_gender) {
                this.member_gender = member_gender;
            }

            public String getMember_mobile() {
                return member_mobile;
            }

            public void setMember_mobile(String member_mobile) {
                this.member_mobile = member_mobile;
            }

            public String getCurrent_store_id() {
                return current_store_id;
            }

            public void setCurrent_store_id(String current_store_id) {
                this.current_store_id = current_store_id;
            }

            public long getReg_time() {
                return reg_time;
            }

            public void setReg_time(long reg_time) {
                this.reg_time = reg_time;
            }

            public String getMember_avatar() {
                return member_avatar;
            }

            public void setMember_avatar(String member_avatar) {
                this.member_avatar = member_avatar;
            }

            public String getMember_birthday() {
                return member_birthday;
            }

            public void setMember_birthday(String member_birthday) {
                this.member_birthday = member_birthday;
            }

            public int getGroup_id() {
                return group_id;
            }

            public void setGroup_id(int group_id) {
                this.group_id = group_id;
            }

            public String getMember_email() {
                return member_email;
            }

            public void setMember_email(String member_email) {
                this.member_email = member_email;
            }

            public String getRedundant_current_store_status() {
                return redundant_current_store_status;
            }

            public void setRedundant_current_store_status(String redundant_current_store_status) {
                this.redundant_current_store_status = redundant_current_store_status;
            }

            public String getRedundant_current_store_code() {
                return redundant_current_store_code;
            }

            public void setRedundant_current_store_code(String redundant_current_store_code) {
                this.redundant_current_store_code = redundant_current_store_code;
            }

            public String getRedundant_register_store_code() {
                return redundant_register_store_code;
            }

            public void setRedundant_register_store_code(String redundant_register_store_code) {
                this.redundant_register_store_code = redundant_register_store_code;
            }

            public String getRedundant_upgrade_store_code() {
                return redundant_upgrade_store_code;
            }

            public void setRedundant_upgrade_store_code(String redundant_upgrade_store_code) {
                this.redundant_upgrade_store_code = redundant_upgrade_store_code;
            }

            public String getRegister_product_code() {
                return register_product_code;
            }

            public void setRegister_product_code(String register_product_code) {
                this.register_product_code = register_product_code;
            }

            public String getUpgrade_product_code() {
                return upgrade_product_code;
            }

            public void setUpgrade_product_code(String upgrade_product_code) {
                this.upgrade_product_code = upgrade_product_code;
            }

            public long getUpgrade_time() {
                return upgrade_time;
            }

            public void setUpgrade_time(long upgrade_time) {
                this.upgrade_time = upgrade_time;
            }

            public String getStore_image() {
                return store_image;
            }

            public void setStore_image(String store_image) {
                this.store_image = store_image;
            }

            public int getRegister_channel() {
                return register_channel;
            }

            public void setRegister_channel(int register_channel) {
                this.register_channel = register_channel;
            }

            public String getMember_remark() {
                return member_remark;
            }

            public void setMember_remark(String member_remark) {
                this.member_remark = member_remark;
            }
        }

        public String getCant_be_used_reason() {
            return cant_be_used_reason;
        }

        public void setCant_be_used_reason(String cant_be_used_reason) {
            this.cant_be_used_reason = cant_be_used_reason;
        }

        public String getProduct_image() {
            return product_image;
        }

        public void setProduct_image(String product_image) {
            this.product_image = product_image;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public double getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(double unit_price) {
            this.unit_price = unit_price;
        }

        public boolean isCoupon_can_be_used() {
            return coupon_can_be_used;
        }

        public void setCoupon_can_be_used(boolean coupon_can_be_used) {
            this.coupon_can_be_used = coupon_can_be_used;
        }

        public String getProduct_group() {
            return product_group;
        }

        public void setProduct_group(String product_group) {
            this.product_group = product_group;
        }

        public MemberInfo getMembers() {
            return members;
        }

        public void setMembers(MemberInfo members) {
            this.members = members;
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

    public ProductModel getModel() {
        return model;
    }

    public void setModel(ProductModel model) {
        this.model = model;
    }
}
