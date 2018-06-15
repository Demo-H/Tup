package com.tupperware.huishengyi.entity;

import com.android.dhunter.common.base.baseadapter.entity.MultiItemEntity;
import com.tupperware.huishengyi.config.Constant;

import java.util.List;

/**
 * Created by dhunter on 2018/3/8.
 */

public class LoveVipIndex {
    public String code;
    public List<ItemInfoListBean> itemInfoList;

    public static class ItemInfoListBean implements MultiItemEntity {

        public String itemType;
        public String module;
        public List<ItemContentListBean> itemContentList;

        @Override
        public int getItemType() {
            if("vipProject".equals(itemType)){
                return Constant.TYPE_VIP_PROJECT;
            }
            else if("memberMaintenance".equals(itemType)){
                return Constant.TYPE_MEMBER_MAINTENANCE;
            }
            else if("memberAttribute".equals(itemType)){
                return Constant.TYPE_MEMBER_ATTRIBUTE;
            }
            else if("memberData".equals(itemType)){
                return Constant.TYPE_MEMBER_DATA;
            }
            return Constant.TYPE_TITLE;
        }

        public int getSpanSize() {
            return 4;
        }

        public static class ItemContentListBean {
            /**
             */

            public String imageUrl;
            public String clickUrl;
            public String itemTitle;
            public String itemSubTitle;
            public String itemSubscript;
            public String itemRecommendedLanguage;
            public String itemBackgroundImageUrl;

        }
    }
}
