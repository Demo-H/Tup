package com.tupperware.biz.entity.member;

import com.tupperware.biz.entity.BaseData;

import java.io.Serializable;

/**
 * Created by dhunter on 2018/6/12.
 */

public class MemberAddBean extends BaseData implements Serializable{

    private MemberAddModel model;

    public class MemberAddModel implements Serializable {
        private int addFansCount;   //今日新增粉丝数
        private int addCommonMemberCount;  //今日新增普通会员数
        private int addVipMemberCount;  //今日新增高级会员数

        public int getAddFansCount() {
            return addFansCount;
        }

        public void setAddFansCount(int addFansCount) {
            this.addFansCount = addFansCount;
        }

        public int getAddCommonMemberCount() {
            return addCommonMemberCount;
        }

        public void setAddCommonMemberCount(int addCommonMemberCount) {
            this.addCommonMemberCount = addCommonMemberCount;
        }

        public int getAddVipMemberCount() {
            return addVipMemberCount;
        }

        public void setAddVipMemberCount(int addVipMemberCount) {
            this.addVipMemberCount = addVipMemberCount;
        }
    }

    public MemberAddModel getModel() {
        return model;
    }

    public void setModel(MemberAddModel model) {
        this.model = model;
    }
}
