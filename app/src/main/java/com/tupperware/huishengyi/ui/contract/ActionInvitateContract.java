package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.member.ActionMembersBean;

/**
 * Created by dhunter on 2018/6/14.
 */

public class ActionInvitateContract {
    public interface View {
        void setActionInvitateData(ActionMembersBean mBean);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
    }

    public interface Presenter {
        void getActionInvitateData(long infoId, long storeId);
    }
}
