package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.RemindMemberResponse;

/**
 * Created by dhunter on 2018/11/29.
 */

public class RemindContract {
    public interface View {
        void setFilterReservationData(RemindMemberResponse remindBean);
        void setMoreFilterReservationData(RemindMemberResponse remindBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getFilterReservation(long chooseTime);
        void getMoreFilterReservation(long chooseTime, int page);
    }
}
