package com.tupperware.biz.entity;

import com.google.gson.Gson;

/**
 * Created by dhunter on 2018/10/12.
 */

public class WeChatFansInfo {

    public static WeChatFansInfo createInstanceByJson(String json) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, WeChatFansInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private long memberId;
    private String city;
    private String country;
    private String headimgurl;
    private String language;
    private String nickname;
    private String province;
    private int sex;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
