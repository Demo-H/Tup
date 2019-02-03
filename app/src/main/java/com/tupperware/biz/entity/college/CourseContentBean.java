package com.tupperware.biz.entity.college;

import com.android.dhunter.common.baserecycleview.entity.MultiItemEntity;

/**
 * Created by dhunter on 2018/5/10.
 * 数据解析为课程章节列表
 */

public class CourseContentBean implements MultiItemEntity {

    public static final int HEADER = 1;
    public static final int CONTENT = 2;
    public static final int FOOTER = 3;

    public int itemType;
    public String chapter;
    public String hour;
    public String teacher;
    public String fileName;
    public String filePath;
    public int section;

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }
}
