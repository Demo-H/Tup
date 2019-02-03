package com.tupperware.biz.entity.home;

import com.android.dhunter.common.baserecycleview.entity.MultiItemEntity;
import com.tupperware.biz.config.Constant;

import java.util.List;

/**
 * Created by dhunter on 2018/4/25.
 * 服务器数据需要进行type分类处理
 */

public class HomeIndexBean {
    public boolean success;
    public String resultCode;
    public String message;
    public List<ItemInfoListBean> itemInfoList;
    public static class ItemInfoListBean implements MultiItemEntity {
        public String itemType;
        public List<ItemContentListBean> itemContentList;

        @Override
        public int getItemType() {
            if("topBanner".equals(itemType)){
                return Constant.TYPE_TOP_BANNER;
            }
            else if("preciseRecommendation".equals(itemType)){
                return Constant.TYPE_PRECISE_RECOMMENDATION;
            }
            else if("iconList".equals(itemType)){
                return Constant.TYPE_ICON_LIST;
            }
            else if("targetMe".equals(itemType)){
                return Constant.TYPE_TARGET_ME;
            }
            else if("marketInfo".equals(itemType)){
                return Constant.TYPE_MARKET_INFO;
            }
            else if("type_Title".equals(itemType)){
                return Constant.TYPE_TITLE;
            }
            return Constant.TYPE_TITLE;
        }

        public static class ItemContentListBean {
            /**
             * imageUrl :
             * clickUrl :
             */
            public int id;
            public String title;
            public String subTitle;
            public String imgUrl;
            public String link;
            public int image;
            public String author;
            public int forwardNum;
            public int readNum;
        }

        public int getSpanSize() {
            return 4;
        }

        public List<ItemContentListBean> getItemContentList() {
            return itemContentList;
        }

        public void setItemContentList(List<ItemContentListBean> itemContentList) {
            this.itemContentList = itemContentList;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }
    }

    public List<ItemInfoListBean> getItemInfoList() {
        return itemInfoList;
    }

    public void setItemInfoList(List<ItemInfoListBean> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }
}
