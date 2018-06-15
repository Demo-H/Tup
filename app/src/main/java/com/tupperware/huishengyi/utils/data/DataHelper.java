package com.tupperware.huishengyi.utils.data;

import com.tupperware.huishengyi.entity.college.CourseBean;
import com.tupperware.huishengyi.entity.college.CourseContentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhunter on 2018/5/10.
 */

public class DataHelper {

    public static List<CourseContentBean> getDatamatch(List<CourseBean.CourseModel.OutLineFront> outlineFrontList) {
        List<CourseContentBean> dataList = new ArrayList<>();
        //遍历每一章节
        for(CourseBean.CourseModel.OutLineFront front : outlineFrontList) {
            CourseContentBean courseheader = new CourseContentBean();
            courseheader.setChapter(front.getChapter());
            courseheader.setItemType(CourseContentBean.HEADER);

            List<CourseBean.CourseModel.OutLineFront.OutLine> outLineContentList = front.getOutlineList();
            List<CourseContentBean> courseContentList = new ArrayList<>();
            int i = 1; //标记课时
            for(CourseBean.CourseModel.OutLineFront.OutLine outLineContent : outLineContentList) {
                CourseContentBean courseContenItem = new CourseContentBean();
                courseContenItem.setChapter(outLineContent.getChapter());
                courseContenItem.setHour(outLineContent.getHour());
                courseContenItem.setTeacher(outLineContent.getTeacher());
                courseContenItem.setFileName(outLineContent.getFileName());
                courseContenItem.setFilePath(outLineContent.getFilePath());
                courseContenItem.setItemType(CourseContentBean.CONTENT);
                courseContenItem.setSection(i++);
                courseContentList.add(courseContenItem);
            }
            dataList.add(courseheader);
            dataList.addAll(courseContentList);
        }
        /**
         * 添加一个分割线作为footer
         */
        CourseContentBean coursefooter = new CourseContentBean();
        coursefooter.setItemType(CourseContentBean.FOOTER);
        dataList.add(coursefooter);
        return dataList;
    }
}
