package com.tupperware.biz.entity.member;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/6/7.
 */

public class StoreScheduleBean extends BaseData {

    private List<ActionModel> models;

    public class ActionModel{

        private long infoId;
        private String title;
        private String imgUrl;
        private long storeId;
        private long enrollNum;
        private String link;

        public long getInfoId() {
            return infoId;
        }

        public void setInfoId(long infoId) {
            this.infoId = infoId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
        }

        public long getEnrollNum() {
            return enrollNum;
        }

        public void setEnrollNum(long enrollNum) {
            this.enrollNum = enrollNum;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public List<ActionModel> getModels() {
        return models;
    }

    public void setModels(List<ActionModel> models) {
        this.models = models;
    }
}
