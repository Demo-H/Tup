package com.tupperware.biz.entity;

import java.util.List;

/**
 * Created by dhunter on 2018/3/19.
 */

public class StoreBean {
    public String typeName;
    public String code;
    public int typeId;
    public List<ContentBean> content;

    public static class ContentBean {
        public int storeId;
        public String storeName;
        public String storeAddr;
        public String storeTel;
        public String imageUrl;
        public int image;
        public boolean isSelected;
        public int isSelectedimage;
        public int isNotSelectedimage;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
