package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.college.CollegeBean;

/**
 * Created by dhunter on 2018/4/18.
 */

public class CollExperienceContract {
    public interface View {
        void setExperienceData(CollegeBean mBean);
        void setMoreExperienceData(CollegeBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
    }

    public interface Presenter {
        void getExperienceData(int tagId);
        void getMoreExperienceData(int tagId, int pageIndex);
    }
}
