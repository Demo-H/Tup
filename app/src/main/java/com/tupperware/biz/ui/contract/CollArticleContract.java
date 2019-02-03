package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.college.CollegeBean;

/**
 * Created by dhunter on 2018/4/17.
 */

public class CollArticleContract {
    public interface View {
        void setArticleData(CollegeBean mBean);
        void setMoreArticleData(CollegeBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
    }

    public interface Presenter {
        void getArticleData(int tagId);
        void getMoreArticleData(int tagId, int pageIndex);
    }
}
