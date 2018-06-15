package com.tupperware.huishengyi.entity;

/**
 * Created by dhunter on 2018/3/9.
 */

public class TagBean {

    public TagBean(String tag_id, String tag_name, String count) {
        this.tag_id = tag_id;
        this.tag_name = tag_name;
        this.count = count;
    }

    /**
     * tag_id : 55
     * tag_name : 准时
     */


    private String tag_id;
    private String tag_name;
    private String count;

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
