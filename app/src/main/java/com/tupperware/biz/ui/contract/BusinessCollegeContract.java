package com.tupperware.biz.ui.contract;


import com.tupperware.biz.entity.college.CollegeTabBean;

/**
 * Created by dhunter on 2018/4/28.
 */

public class BusinessCollegeContract {

    public interface View {
        void setLableData(CollegeTabBean mBean);
        void setNetErrorView();
        void setNormalView();
        void toast(String msg);
    }

    public interface Presenter {
        void getLableData();
    }
}
