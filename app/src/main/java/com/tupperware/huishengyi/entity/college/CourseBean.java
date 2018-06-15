package com.tupperware.huishengyi.entity.college;

import com.tupperware.huishengyi.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/4/17.
 */

public class CourseBean extends BaseData {

    public CourseModel model;

    public static class CourseModel{
        public long id;
        public String title;
        public String content;
        public int learnNum;
        public List<OutLineFront> outlineFrontList;

        public static class OutLineFront{
            public String chapter;
            public List<OutLine> outlineList;

            public static class OutLine{
                public String chapter;
                public String hour;
                public String teacher;
                public String fileName;
                public String filePath;

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
            }

            public String getChapter() {
                return chapter;
            }

            public void setChapter(String chapter) {
                this.chapter = chapter;
            }

            public List<OutLine> getOutlineList() {
                return outlineList;
            }

            public void setOutlineList(List<OutLine> outlineList) {
                this.outlineList = outlineList;
            }
        }
    }

}