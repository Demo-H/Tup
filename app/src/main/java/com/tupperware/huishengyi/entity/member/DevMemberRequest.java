package com.tupperware.huishengyi.entity.member;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dhunter on 2018/6/6.
 */

public class DevMemberRequest implements Serializable {

    private String currentStoreId;
    private String employeeCode;
    private String mobile;
    private List<TagsCode> tags;

    public static class TagsCode implements Serializable{
        private String tagCode;

        public String getTagCode() {
            return tagCode;
        }

        public void setTagCode(String tagCode) {
            this.tagCode = tagCode;
        }
    }

    public String getCurrentStoreId() {
        return currentStoreId;
    }

    public void setCurrentStoreId(String currentStoreId) {
        this.currentStoreId = currentStoreId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<TagsCode> getTags() {
        return tags;
    }

    public void setTags(List<TagsCode> tags) {
        this.tags = tags;
    }
}
