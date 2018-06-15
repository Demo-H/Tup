package com.tupperware.huishengyi.entity.home;

/**
 * Created by dhunter on 2018/4/26.
 */

public class VersionCheckRequest {
    public String userId;
    public String version;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
