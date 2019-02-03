package com.tupperware.biz.entity.member;

import com.tupperware.biz.config.Constant;

import java.io.Serializable;


/**
 * Created by dhunter on 2018/10/25.
 */

public class MemberSearchConditionDTO implements Serializable {
    public String groupId;  //会员等级(0普通会员,1高级会员,2粉丝),多个用,隔开例(0,1,2) ,
    public Integer integralAmountEnd;  //积分结束区间 ,
    public Integer integralAmountStart;  //积分开始区间 ,
    public String orderStatus;  //礼品状态 ,
    public String productType;  //产品类型 多个用,隔开例 ,
    public Integer recentConsumption;  //最近消费（1—>1个月、2->3个月、3->6个月） ,
    public String registerTimeEnd;  //入会时间结束区间 ,
    public String registerTimeStart;  //入会时间开始区间 ,
    public String searchKey;  //礼品订单号/注册唯一码/手机号/姓名 ,
    public Integer searchType; //  1、礼品订单号2、注册唯一码3、手机号4、姓名 ,
    public int page;
    public int size;
    public Integer storeId;
    public SortConditionDTO sortConditionDTO; //

    public class SortConditionDTO {
        public String ascOrDesc; // 升序或降序,asc升序、desc降序
        public int sortKey;  //排序关键字，0入会时间、1积分总额、2申请礼品时间, 3综合排序
        public SortConditionDTO(){}
        public SortConditionDTO(String ascOrDesc, int sortKey) {
            this.ascOrDesc = ascOrDesc;
            this.sortKey = sortKey;
        }
    }


    public static final int MEMBER_TYPE_VIP = 0;  //普通会员
    public static final int MEMBER_TYPE_ADVANCED_VIP = 1; //高级会员
    public static final int MEMBER_TYPE_FANS = 2;  //粉丝

    public static final String SORT_ORDER_ASCE = "asc"; //从小到大排序 -- 升序
    public static final String SORT_ORDER_DESC = "desc";  //从大到小排序 -- 降序

    public static final int TYPE_SORT_INITIATION = 0;  //0入会时间
    public static final int TYPE_SORT_INTEGRAL = 1;  //积分总额
    public static final int TYPE_SORT_GIFT = 2;  //申请礼品时间
    public static final int SORT_ORDER_ALL = 3;  //综合排序

    public static final int RECENTLY_CONSUME_TIME_ONE = 1;  //最近一个月消费
    public static final int RECENTLY_CONSUME_TIME_THREE = 2;  //最近三个月消费
    public static final int RECENTLY_CONSUME_TIME_SIX = 3;  //最近6个月消费

    /**
     * 礼品订单状态(1表示已申请, 2表示已发货, 3表示已收货)
     */
    public static final int GIFT_REQUEST = 1;
    public static final int GIFT_SEND = 2;
    public static final int GIFT_RECEIVED = 3;
    public static final int GIFT_DEFAULT = -1;


    private static MemberSearchConditionDTO mInstance;

    public static MemberSearchConditionDTO getInstance() {
        if(mInstance == null) {
            mInstance = new MemberSearchConditionDTO();
            mInstance.reset();
        }
        return mInstance;
    }

    /**
     * 重置筛选条件
     */
    public void reset() {
        groupId = "";
        integralAmountEnd = null;
        integralAmountStart = null;
        orderStatus = "";
        productType = "";
        recentConsumption = null;
        registerTimeStart = null;
        registerTimeEnd = null;
        clearSearchCondition();
        if(sortConditionDTO == null) {
            sortConditionDTO = new SortConditionDTO();
        }
        sortConditionDTO.ascOrDesc = SORT_ORDER_DESC;
        sortConditionDTO.sortKey = TYPE_SORT_INITIATION;
        page = Constant.FIRST_PAGE_INDEX;
        size = Constant.DEFAULT_MEMBER_PAGE_SIZE;
    }


    public void clearSearchCondition() {
        searchKey = "";
        searchType = null;
    }


}
