package com.tupperware.biz.entity.member;

/**
 * Created by dhunter on 2018/11/29.
 */

public class RemindMemberInfo {
    private int gender;//  性别，memberID为0是默认为1 ,
    private int groupId;//  会员等级，memberID为0是默认为99 ,
    private int memberId;//  会员ID ,
    private String mobile;//  联系电话 ,
    private String name;//  姓名 ,
    private long regTime;//  最后完成时间

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRegTime() {
        return regTime;
    }

    public void setRegTime(long regTime) {
        this.regTime = regTime;
    }
}
