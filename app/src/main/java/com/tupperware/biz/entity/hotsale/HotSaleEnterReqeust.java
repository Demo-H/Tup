package com.tupperware.biz.entity.hotsale;

import java.util.List;

/**
 * Created by dhunter on 2018/12/3.
 */

public class HotSaleEnterReqeust {

    private Integer storeId;

    private List<HotSaleEnterInfo> chilList;

    public List<HotSaleEnterInfo> getChilList() {
        return chilList;
    }

    public void setChilList(List<HotSaleEnterInfo> chilList) {
        this.chilList = chilList;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
