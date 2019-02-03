package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.ActionMembersBean;

/**
 * Created by dhunter on 2018/6/14.
 */

public class ReservationActionContract {

    public interface View {
        void setMemberActionDetialData(ActionMembersBean mBean);
        void toast(String msg);
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getMemberActionDetialData(long id);
    }
}
