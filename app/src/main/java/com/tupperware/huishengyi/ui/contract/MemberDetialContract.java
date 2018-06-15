package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.member.MemberBean;

/**
 * Created by dhunter on 2018/5/31.
 */

public class MemberDetialContract {

    public interface View {
        void refreshUIData(MemberBean memberBean);
        void toast(String msg);
        void hideDialog();
    }

    public interface Presenter {
        void getMemberDetialData(long member_id);
    }
}
