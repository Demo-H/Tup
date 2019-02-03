package com.tupperware.biz.entity.home;

/**
 * Created by dhunter on 2018/4/26.
 */

public class VersionCheckRequest {
    public String device; //设备：ios、android ,
    private String platform; //平台, wechat-shop：惠厨房，pos：惠生意，manage：惠管理
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
