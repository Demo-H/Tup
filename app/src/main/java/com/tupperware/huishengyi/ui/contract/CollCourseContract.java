package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.college.CollegeBean;

/**
 * Created by dhunter on 2018/4/17.
 */

public class CollCourseContract {
    public interface View {
        void setCourseData(CollegeBean mBean);
        void setMoreCourseData(CollegeBean mBean);
        void setBannerView(CollegeBean mBean);
        void toast(String msg);
    }

    public interface Presenter {
        void getCourseData(int tagId);
        void getMoreCourseData(int tagId, int pageIndex);
        void getAdvertData();
    }
}