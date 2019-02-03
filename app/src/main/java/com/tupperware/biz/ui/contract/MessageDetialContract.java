package com.tupperware.biz.ui.contract;


import com.tupperware.biz.entity.msg.MsgItemBean;

/**
 * Created by dhunter on 2018/3/26.
 */

public class MessageDetialContract {

    public interface View {
        void setMessageDetialData(MsgItemBean msg);
        void setMoreMessageDetialData(MsgItemBean msg);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void reLogin();
    }

    public interface Presenter {
        void getMessageDetialData(long msgTagId);
        void getMoreMessageDetialData(long msgTagId, long timestamp);
    }
}
