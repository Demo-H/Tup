package com.tupperware.huishengyi.entity.msg;

/**
 * Created by dhunter on 2018/5/23.
 */

public class MsgRedTip {
    public boolean success;
    public ReadModel model;

    public class ReadModel {
        public int unRead;
    }
}
