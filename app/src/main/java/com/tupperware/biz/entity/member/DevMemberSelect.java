package com.tupperware.biz.entity.member;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dhunter on 2018/6/6.
 */

public class DevMemberSelect implements Serializable {
    private String mobile;
    private String isLike;
    private String isWantStudy;
    private List<DevMemberRequest.TagsCode> tasteTags;
    private String isHave;
    private String isClean;
    private String familystatus;
    private String moneystatus;
    private List<DevMemberRequest.TagsCode> productTags;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getIsWantStudy() {
        return isWantStudy;
    }

    public void setIsWantStudy(String isWantStudy) {
        this.isWantStudy = isWantStudy;
    }

    public List<DevMemberRequest.TagsCode> getTasteTags() {
        return tasteTags;
    }

    public void setTasteTags(List<DevMemberRequest.TagsCode> tasteTags) {
        this.tasteTags = tasteTags;
    }

    public String getIsHave() {
        return isHave;
    }

    public void setIsHave(String isHave) {
        this.isHave = isHave;
    }

    public String getIsClean() {
        return isClean;
    }

    public void setIsClean(String isClean) {
        this.isClean = isClean;
    }

    public String getFamilystatus() {
        return familystatus;
    }

    public void setFamilystatus(String familystatus) {
        this.familystatus = familystatus;
    }

    public String getMoneystatus() {
        return moneystatus;
    }

    public void setMoneystatus(String moneystatus) {
        this.moneystatus = moneystatus;
    }

    public List<DevMemberRequest.TagsCode> getProductTags() {
        return productTags;
    }

    public void setProductTags(List<DevMemberRequest.TagsCode> productTags) {
        this.productTags = productTags;
    }
}
