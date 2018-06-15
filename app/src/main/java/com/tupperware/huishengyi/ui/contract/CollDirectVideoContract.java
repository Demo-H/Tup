package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.college.CollegeBean;

/**
 * Created by dhunter on 2018/4/18.
 */

public class CollDirectVideoContract {

    public interface View {
        void setDirectVideoData(CollegeBean mBean);
        void setMoreDirectVideoData(CollegeBean mBean);
    }

    public interface Presenter {
        void getDirectVideoData();
        void getMoreDirectVideoData();
    }
}
