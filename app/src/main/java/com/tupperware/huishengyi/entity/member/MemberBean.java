package com.tupperware.huishengyi.entity.member;

import com.google.gson.Gson;
import com.tupperware.huishengyi.entity.BaseData;

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
        public int total;
        public int pages;
        public boolean hasMore;
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
        public long member_id;  //会员Id
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
        public int register_channel;
        public String register_product_code;
        public String upgrade_product_code;
        public long upgrade_time;
        public String avatar;   //会员头像
        public String member_birthday;
        public int member_coupon_total; // 专享礼券数
        public String current_store; // 会员所属专卖店
        public String register_store; //会员入会专卖店
        public String member_remark; // 会员备注

    }

    public List<MemberInfo> getModels() {
        return models;
    }

    public void setModels(List<MemberInfo> models) {
        this.models = models;
    }
}
