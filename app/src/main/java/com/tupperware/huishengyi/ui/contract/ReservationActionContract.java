package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.member.ActionMembersBean;

/**
 * Created by dhunter on 2018/6/14.
 */

public class ReservationActionContract {

    public interface View {
        void setMemberActionDetialData(ActionMembersBean mBean);
        void toast(String msg);
        void hideDialog();
    }

    public interface Presenter {
        void getMemberActionDetialData(long id);
    }
}
