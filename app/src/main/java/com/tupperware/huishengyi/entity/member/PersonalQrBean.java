package com.tupperware.huishengyi.entity.member;

import com.tupperware.huishengyi.entity.BaseData;

/**
 * Created by dhunter on 2018/6/4.
 */

public class PersonalQrBean extends BaseData {

    public QrContent model;

    public class QrContent{
        public String name;
        public String code;
        public String logo;
    }
}
