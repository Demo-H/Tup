package com.tupperware.huishengyi.entity.saleenter;

import com.tupperware.huishengyi.entity.BaseData;

import java.util.List;

/**
 * Created by dhunter on 2018/5/25.
 */

public class SaleTabBean extends BaseData {

    public List<TabModel> models;

    public static class TabModel{
        public int id;
        public String name;
        public int isCustom;
        public int isDelete;
    }

}