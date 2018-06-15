package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.member.RemarksRequest;

/**
 * Created by dhunter on 2018/6/6.
 */

public class RemarksContract {

    public interface View {
        void toast(String msg);
        void setremarksSuccess();
        void hideDialog();
    }

    public interface Presenter {
        void submitRemarks(RemarksRequest remarksRequest);
    }
}
