package com.tupperware.biz.entity.home;

import com.tupperware.biz.entity.BaseData;

/**
 * Created by dhunter on 2018/4/25.
 */

public class VersionUpBean extends BaseData {

    public Model model;

    public class Model {
        public String version;
        public String installUrl;
        public String versionDesc;
        public int isForce;  //1 表示强制升级，0表示不强制
        public int isUpToDate;  //0 表示需要升级，1表示当前是最新版本不需要升级

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getInstallUrl() {
            return installUrl;
        }

        public void setInstallUrl(String installUrl) {
            this.installUrl = installUrl;
        }

        public String getVersionDesc() {
            return versionDesc;
        }

        public void setVersionDesc(String versionDesc) {
            this.versionDesc = versionDesc;
        }

        public int getIsForce() {
            return isForce;
        }

        public void setIsForce(int isForce) {
            this.isForce = isForce;
        }

        public int getIsUpToDate() {
            return isUpToDate;
        }

        public void setIsUpToDate(int isUpToDate) {
            this.isUpToDate = isUpToDate;
        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
