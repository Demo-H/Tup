package com.tupperware.biz.entity;

import com.android.dhunter.common.baserecycleview.entity.MultiItemEntity;
import com.tupperware.biz.config.Constant;

import java.util.List;

/**
 * Created by dhunter on 2018/3/11.
 */

public class HomeIndex {

    /**
     * code : 0
     * itemInfoList :
     */

    public String code;
    public List<ItemInfoListBean> itemInfoList;

    public static class ItemInfoListBean implements MultiItemEntity {

        public String itemType;
        public String module;
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

        public int getSpanSize() {

//            if("iconList".equals(itemType)){
//                return 2;
//            }
            return 4;
        }

        public static class ItemContentListBean {
            /**
             * imageUrl :
             * clickUrl :
             */

            public String imageUrl;
            public String clickUrl;
            public String itemTitle;
            public String itemSubTitle;
            public String itemSubscript;
            public String itemRecommendedLanguage;
            public String itemBackgroundImageUrl;
            public String authorName;
            public int pageView;
            public int image;

            public int getImage() {
                return image;
            }

            public void setImage(int image) {
                this.image = image;
            }
        }
    }
}
