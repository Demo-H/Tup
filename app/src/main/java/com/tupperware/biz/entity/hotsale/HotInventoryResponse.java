package com.tupperware.biz.entity.hotsale;

import com.tupperware.biz.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/11/30.
 */

public class HotInventoryResponse extends BaseData {

    private List<HotInventoryInfo> models;
    private Extra extra;

    public class Extra {
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public List<HotInventoryInfo> getModels() {
        return models;
    }

    public void setModels(List<HotInventoryInfo> models) {
        this.models = models;
    }

}
