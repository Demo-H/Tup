package com.tupperware.biz.ui.contract;


import com.tupperware.biz.entity.college.CourseBean;

/**
 * Created by dhunter on 2018/5/9.
 */

public class CourseDetialContract {
    public interface View {
        void setCourseDetialData(CourseBean mBean);
        void toast(String msg);
    }

    public interface Presenter {
        void getCourseDetialData(long tagId);
    }
}
