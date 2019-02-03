package com.tupperware.biz.entity.member;

import com.google.gson.Gson;
import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/5/24.
 */

public class MemberBean extends BaseData {

    public static MemberBean createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, MemberBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public MemberInfo model; //会员详情
    public List<MemberInfo> models; //会员列表
    public PageInfo pageInfo;
    public Extra extra;

    public class PageInfo{
        private boolean hasNextPage;
        private List<MemberInfo> list;
        public int total;
        public int pages;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public List<MemberInfo> getList() {
            return list;
        }

        public void setList(List<MemberInfo> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }
    }

    public class Extra {
        public MemPermissions permissions;
        public List<MemberTag> memberTags;

        public class MemberTag {
            public String tagName;
        }

        public class MemPermissions {
            public boolean allowed_address;
            public boolean allowed_phone;
        }

    }

    public class MemberInfo{
        public Integer member_id;  //会员Id
//        public String member_status;
        public String name;   //会员名称
        public String mobile;  //会员联系方式
        public int group_id;   // 会员类别 0普通1高级 2粉丝 -1或其它全部
        public int integral_total; //惠金币总收入
        public int integral_amount; //惠金币剩余金币
        public String member_address; //会员地址
        public long reg_time;  // 注册时间, 已升级会员取升级时间
        public int is_upgrade; //是否已升级 0未升级 1已升级
        public int gender;  //性别 0男1女
        public String group_name;
        public String register_channel;
        public String register_product_code;
        public String upgrade_product_code;
        public long upgrade_time;
        public String avatar;   //会员头像
        public String member_birthday;
        public int member_coupon_total; // 专享礼券数
        public String current_store; // 会员所属专卖店
        public String register_store; //会员入会专卖店
        public String member_remark; // 会员备注
        private String gift_apply_status; // 礼品状态

        public Integer getMember_id() {
            return member_id;
        }

        public void setMember_id(Integer member_id) {
            this.member_id = member_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public int getIntegral_total() {
            return integral_total;
        }

        public void setIntegral_total(int integral_total) {
            this.integral_total = integral_total;
        }

        public int getIntegral_amount() {
            return integral_amount;
        }

        public void setIntegral_amount(int integral_amount) {
            this.integral_amount = integral_amount;
        }

        public String getMember_address() {
            return member_address;
        }

        public void setMember_address(String member_address) {
            this.member_address = member_address;
        }

        public long getReg_time() {
            return reg_time;
        }

        public void setReg_time(long reg_time) {
            this.reg_time = reg_time;
        }

        public int getIs_upgrade() {
            return is_upgrade;
        }

        public void setIs_upgrade(int is_upgrade) {
            this.is_upgrade = is_upgrade;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getRegister_channel() {
            return register_channel;
        }

        public void setRegister_channel(String register_channel) {
            this.register_channel = register_channel;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMember_birthday() {
            return member_birthday;
        }

        public void setMember_birthday(String member_birthday) {
            this.member_birthday = member_birthday;
        }

        public int getMember_coupon_total() {
            return member_coupon_total;
        }

        public void setMember_coupon_total(int member_coupon_total) {
            this.member_coupon_total = member_coupon_total;
        }

        public String getCurrent_store() {
            return current_store;
        }

        public void setCurrent_store(String current_store) {
            this.current_store = current_store;
        }

        public String getRegister_store() {
            return register_store;
        }

        public void setRegister_store(String register_store) {
            this.register_store = register_store;
        }

        public String getMember_remark() {
            return member_remark;
        }

        public void setMember_remark(String member_remark) {
            this.member_remark = member_remark;
        }

        public String getGift_apply_status() {
            return gift_apply_status;
        }

        public void setGift_apply_status(String gift_apply_status) {
            this.gift_apply_status = gift_apply_status;
        }
    }

    public MemberInfo getModel() {
        return model;
    }

    public void setModel(MemberInfo model) {
        this.model = model;
    }

    public List<MemberInfo> getModels() {
        return models;
    }

    public void setModels(List<MemberInfo> models) {
        this.models = models;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
