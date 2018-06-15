package com.tupperware.huishengyi.entity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/22.
 */

public class StaffManagerBean {

    public int typeId;
    public String typeName;
    public String code;
    public List<StaffContentBean> content;

    public static class StaffContentBean {

        public String no;
        public String name;
        public String userTel;
        public int sex;  //性别
        public String hiredate;
        public String password;
        public int status;
    }
}
