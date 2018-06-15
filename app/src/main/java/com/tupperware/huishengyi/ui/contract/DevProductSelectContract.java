package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.member.DevMemberRequest;
import com.tupperware.huishengyi.entity.member.DevMemberRespone;

/**
 * Created by dhunter on 2018/6/6.
 */

public class DevProductSelectContract {

    public interface View {
        void toast(String msg);
        void setDevMemberData(DevMemberRespone devMemberRespone);
        void hideDialog();
    }

    public interface Presenter {
        void submitmemberLabel(DevMemberRequest devMemberRequest);
    }
}
