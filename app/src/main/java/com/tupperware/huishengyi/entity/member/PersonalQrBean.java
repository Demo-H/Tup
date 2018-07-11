package com.tupperware.huishengyi.entity.member;

import com.tupperware.huishengyi.entity.BaseData;

/**
 * Created by dhunter on 2018/6/4.
 */

public class PersonalQrBean extends BaseData {

    private QrContent model;

    public class QrContent{
        private String name;
        private String code;
        private String logo;

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
    }

    public QrContent getModel() {
        return model;
    }

    public void setModel(QrContent model) {
        this.model = model;
    }
}
