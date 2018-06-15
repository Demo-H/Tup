package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.home.HomeIndexBean;

/**
 * Created by dhunter on 2018/3/12.
 */

public class HomePageContract {
    public interface View {
        void setHomePageData(HomeIndexBean homeBean);
        void showToast(String string);
        void setNormalView();
        void setNetErrorView();
        void setShowMsgRedTip(int unread);
        void reLogin();
//        void setHideMsgRedTip();
//        void setHomePageData(HomeBean.Model homeBean);
//        void setMoreHomePageData(HomeBean homeIndex);
    }

    public interface Presenter {
        void getHomePageData();
        void getMoreHomePageData();
    }
}
