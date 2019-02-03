package com.tupperware.biz.ui.contract;

import android.content.Context;
import android.widget.RelativeLayout;

import com.tupperware.biz.entity.member.PersonalQrBean;

/**
 * Created by dhunter on 2018/3/12.
 */

public class HomePageContract {
    public interface View {
//        void setHomePageData(HomeIndexBean homeBean);
//        void setHomePageData(HomeBean homeBean);
//        void showToast(String string);
//        void setNormalView();
//        void setNetErrorView();
//        void setShowMsgRedTip(int unread);
//        void setHideMsgRedTip();
//        void setHomePageData(HomeBean.Model homeBean);
//        void setMoreHomePageData(HomeBean homeIndex);
        void reLogin();
        void setPersonQrData(PersonalQrBean mBean);
        void toast(String msg);
        void setNetErrorView();
        void setNormalView();
        void setEmptyView();
    }

    public interface Presenter {
//        void getHomePageData();
//        void getMoreHomePageData();
//        void getMsgRedTip();
        void getPersonQrData(String storeCode);
        void saveQrImage(final RelativeLayout mRl, final Context context);
    }
}
