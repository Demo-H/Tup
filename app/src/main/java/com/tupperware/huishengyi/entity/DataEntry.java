package com.tupperware.huishengyi.entity;

/**
 * Created by dhunter on 2018/3/12.
 */

public class DataEntry {
    public int resId;
    public String title;
    public String desc;
    public boolean isChecked;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
