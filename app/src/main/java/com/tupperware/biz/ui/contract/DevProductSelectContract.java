package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.member.DevMemberRequest;
import com.tupperware.biz.entity.member.DevMemberRespone;

/**
 * Created by dhunter on 2018/6/6.
 */

public class DevProductSelectContract {

    public interface View {
        void toast(String msg);
        void setDevMemberData(DevMemberRespone devMemberRespone);
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void submitmemberLabel(DevMemberRequest devMemberRequest);
    }
}
