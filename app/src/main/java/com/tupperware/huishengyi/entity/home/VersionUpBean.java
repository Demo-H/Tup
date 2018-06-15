package com.tupperware.huishengyi.entity.home;

import com.tupperware.huishengyi.entity.BaseData;

/**
 * Created by dhunter on 2018/4/25.
 */

public class VersionUpBean extends BaseData {

    public Model model;

    public class Model {
        public String version;
        public String installUrl;
        public String versionDesc;
        public int isForce;
        public int isUpToDate;  //0 表示需要升级，1表示当前是最新版本不需要升级
    }

}
