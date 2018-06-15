package com.tupperware.huishengyi.entity.member;

import com.tupperware.huishengyi.entity.BaseData;

/**
 * Created by dhunter on 2018/6/6.
 */

public class DevMemberRespone extends BaseData{
    public DevMemberModel model;

    public class DevMemberModel{
        public int code;
        public String qrcode;
    }
}
