package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.college.CollegeBean;

/**
 * Created by dhunter on 2018/4/17.
 */

public class CollCourseContract {
    public interface View {
        void setCourseData(CollegeBean mBean);
        void setMoreCourseData(CollegeBean mBean);
        void setBannerViewHide();
        void setBannerView(CollegeBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
    }

    public interface Presenter {
        void getCourseData(int tagId);
        void getMoreCourseData(int tagId, int pageIndex);
        void getAdvertData();
    }
}
