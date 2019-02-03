package com.tupperware.biz.entity.member;

import com.tupperware.biz.entity.BaseData;

/**
 * Created by dhunter on 2018/6/4.
 */

public class PersonalQrBean extends BaseData {

    private QrContent model;

    public class QrContent{
        private String name;
        private String code;
        private String logo;
        private String link;
        private long id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    public QrContent getModel() {
        return model;
    }

    public void setModel(QrContent model) {
        this.model = model;
    }
}
