package com.tupperware.huishengyi.model;

import android.net.Uri;

import com.tupperware.huishengyi.config.Constants;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dhunter on 2018/3/2.
 */

public class MemberFiltrateCondition implements Serializable {
    /**
     * 全部会员
     */
    public static final int MEMBER_TYPE_ALL = 2;
    /**
     * 普通会员
     */
    public static final int MEMBER_TYPE_VIP = 0;
    /**
     * 高级会员
     */
    public static final int MEMBER_TYPE_ADVANCED_VIP = 1;
    /**
     * 从小到大排序 -- 升序
     */
    public static final int SORT_ORDER_ASCE = 0;
    /**
     * 从大到小排序 -- 降序
     */
    public static final int SORT_ORDER_DESC = 1;
    /**
     * 综合排序
     */
    public static final int SORT_ORDER_ALL = -1;
    /**
     * 入会时间排序
     */
    public static final int TYPE_SORT_INITIATION = 1;
    /**
     * 积分排序
     */
    public static final int TYPE_SORT_INTEGRAL = 2;


    public static final int RECENTLY_CONSUME_TIME_ALL = -1;
    /**
     * 最近一个月消费
     */
    public static final int RECENTLY_CONSUME_TIME_ONE = 1;
    /**
     * 最近三个月消费
     */
    public static final int RECENTLY_CONSUME_TIME_THREE = 3;
    /**
     * 最近6个月消费
     */
    public static final int RECENTLY_CONSUME_TIME_SIX = 6;


    private int group_id = MEMBER_TYPE_ALL; //默认为全部会员
    public  boolean isAll = false;
    private String searchTxt; //搜索内容
    /**
     * 入会时间开始，毫秒数
     */
    private long initiationStart = -1;//入会时间开始
    /**
     * 入会时间结束，毫秒数
     */
    public long initiationEnd = System.currentTimeMillis();//入会时间结束 -- 必选，默认是最后一次刷新时间
    public boolean initiationEndIsEmpty = true;//入会时间结束是否存在
    private int integralMin = -1; //积分最小值
    private int integralMax = -1; //积分最大值
    private int spendingTime = RECENTLY_CONSUME_TIME_ALL; //最近消费时间段
    private String products;//产品类型组合,
    private int initiationOrder = SORT_ORDER_ALL; //入会时间排序
    private int integralOrder = SORT_ORDER_ALL;//积分排序，0表示降序，1表示升序
    public int page = 1; //页面数 -- 必选
    public int num = Constants.NUM; //条数 -- 必选

    private ArrayList<String> productCheckedNames;//选中的产品
    private long initiationTime = 1;//入会时间点。-- 弃掉的了，但是服务还没有改，留着先

    private static MemberFiltrateCondition mInstance = new MemberFiltrateCondition();

    private MemberFiltrateCondition() {
        productCheckedNames = new ArrayList<String>();
    }

    public static MemberFiltrateCondition getInstance(){
        return mInstance;
    }

    /**
     * 重置筛选条件
     */
    public void reset() {
        group_id = MEMBER_TYPE_ALL;
        searchTxt = "";
        initiationStart = -1;
        initiationEnd = System.currentTimeMillis();
        initiationEndIsEmpty = true;
        integralMin = -1;
        integralMax = -1;
        spendingTime = RECENTLY_CONSUME_TIME_ALL;
        if (productCheckedNames != null) {
            productCheckedNames.clear();
        }
        initiationOrder = SORT_ORDER_ALL;
        integralOrder = SORT_ORDER_ALL;
        page = Constants.PAGE_HOME;
        num = Constants.NUM;
        isAll = false;
    }


    public void addToUri(Uri.Builder builder) {
//        if (group_id != MEMBER_TYPE_ALL) {
        builder.appendQueryParameter("group_id", group_id + "");
//        }
        if (searchTxt != null && !searchTxt.isEmpty()) {
            builder.appendQueryParameter("searchTxt", searchTxt);
        }

        if (initiationStart > 0) {
            builder.appendQueryParameter("initiation_start", initiationStart/1000 + "");
        }

        builder.appendQueryParameter("initiation_end", initiationEnd/1000 + "");

        if (integralMin > 0) {
            builder.appendQueryParameter("integralMin", integralMin + "");
        }
        if (integralMax > 0) {
            builder.appendQueryParameter("integralMax", integralMax + "");
        }

        if (spendingTime > 0) {
            builder.appendQueryParameter("spending_time", spendingTime + "");
        }

        if (productCheckedNames != null && !productCheckedNames.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : productCheckedNames) {
                stringBuilder.append(str);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            builder.appendQueryParameter("products", stringBuilder.toString());
        }
        if (initiationOrder >= 0) {
            builder.appendQueryParameter("initiationOrder", initiationOrder + "");
        }

        if (integralOrder >= 0) {
            builder.appendQueryParameter("integralOrder", integralOrder + "");
        }

        builder.appendQueryParameter("page", page + "");
        builder.appendQueryParameter("num", num + "");
    }

    public boolean isEmpty() {
        boolean memberType = !isAll&&group_id==MEMBER_TYPE_ALL;
        return memberType&& initiationStart < 0 && initiationEndIsEmpty && integralMin < 0 && integralMax < 0 && spendingTime == RECENTLY_CONSUME_TIME_ALL &&
                productCheckedNames.isEmpty();
    }

    @Override
    public String toString() {
        return "MemberFiltrateCondition{" +
                "group_id=" + group_id +
                ", searchTxt='" + searchTxt + '\'' +
                ", initiationStart=" + initiationStart +
                ", initiationEnd=" + initiationEnd +
                ", initiationEndIsEmpty=" + initiationEndIsEmpty +
                ", integralMin=" + integralMin +
                ", integralMax=" + integralMax +
                ", spendingTime=" + spendingTime +
                ", products='" + products + '\'' +
                ", initiationOrder=" + initiationOrder +
                ", integralOrder=" + integralOrder +
                ", page=" + page +
                ", num=" + num +
                ", productCheckedNames=" + productCheckedNames +
                ", initiationTime=" + initiationTime +
                '}';
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getSearchTxt() {
        return searchTxt;
    }

    public void setSearchTxt(String searchTxt) {
        this.searchTxt = searchTxt;
    }

    public int getIntegralMin() {
        return integralMin;
    }

    public void setIntegralMin(int integralMin) {
        this.integralMin = integralMin;
    }

    public int getIntegralMax() {
        return integralMax;
    }

    public void setIntegralMax(int integralMax) {
        this.integralMax = integralMax;
    }

    public int getSpendingTime() {
        return spendingTime;
    }

    public void setSpendingTime(int spendingTime) {
        this.spendingTime = spendingTime;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public ArrayList<String> getProductCheckedNames() {
        return productCheckedNames;
    }

    public void setProductCheckedNames(ArrayList<String> productCheckedNames) {
        this.productCheckedNames = productCheckedNames;
    }

    public int getInitiationOrder() {
        return initiationOrder;
    }

    public void setInitiationOrder(int initiationOrder) {
        this.initiationOrder = initiationOrder;
    }

    public long getInitiationTime() {
        return initiationTime;
    }

    public void setInitiationTime(long initiationTime) {
        this.initiationTime = initiationTime;
    }

    public int getIntegralOrder() {
        return integralOrder;
    }

    public void setIntegralOrder(int integralOrder) {
        this.integralOrder = integralOrder;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getInitiationStart() {
        return initiationStart;
    }

    public void setInitiationStart(long initiationStart) {
        this.initiationStart = initiationStart;
    }

    public long getInitiationEnd() {
        return initiationEnd;
    }

    public void setInitiationEnd(long initiationEnd) {
        this.initiationEnd = initiationEnd;
    }
}
