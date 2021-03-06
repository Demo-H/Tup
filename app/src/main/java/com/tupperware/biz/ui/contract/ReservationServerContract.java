package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.ReservationServerBean;

/**
 * Created by dhunter on 2018/5/31.
 */

public class ReservationServerContract {
    public interface View {
        void setReservationServerData(ReservationServerBean mBean);
        void setMoreReservationServerData(ReservationServerBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getReservationServerData(String mobile, String storeCode);
        void getMoreReservationServerData(String mobile, String storeCode, int indexPage);
    }
}
