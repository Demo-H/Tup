package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.member.StoreScheduleBean;

/**
 * Created by dhunter on 2018/6/7.
 */

public class ActionListContract {
    public interface View {
        void setScheduleListData(StoreScheduleBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
    }

    public interface Presenter {
        void getScheduleListData(String storeCode);
    }
}
