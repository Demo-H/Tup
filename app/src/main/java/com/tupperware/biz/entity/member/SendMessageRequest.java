package com.tupperware.biz.entity.member;

/**
 * Created by dhunter on 2018/11/9.
 */

public class SendMessageRequest {
    private String memberMobile;
    private int code;  //0新增粉丝，1粉丝已存在且属于当前门店，2粉丝已存在但属于其他门店 ,

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
