package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.msg.MsgBean;

/**
 * Created by dhunter on 2018/5/22.
 */

public class MessageContract {

    public interface View {
        void setMsgData(MsgBean msg);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
    }

    public interface Presenter {
        void getMsgData();
    }
}
