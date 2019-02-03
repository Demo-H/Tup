package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.MemberBean;

/**
 * Created by dhunter on 2018/5/31.
 */

public class MemberDetialContract {

    public interface View {
        void refreshUIData(MemberBean memberBean);
        void toast(String msg);
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getMemberDetialData(Integer memberId, String mobileNum, int storeId, String employeeCode);
    }
}
