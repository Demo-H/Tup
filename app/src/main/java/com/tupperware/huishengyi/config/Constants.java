package com.tupperware.huishengyi.config;

/**
 * Created by dhunter on 2018/2/1.
 */

public class Constants {
    public static boolean isdebug = false;

    /** pull refresh 属性种类 **/
    public static final int REQUEST_CODE_OUT = 0;
    public static final int REQUEST_CODE_UP = 1;
    public static final int REQUEST_CODE_DOWN = 2;
    public static final int REQUEST_CODE_INIT = 3;
    public static final int REQUEST_CODE_LOGOUT = 4;
    public static final int REQUEST_CODE_LOGIN = 5;
    public static final int REQUEST_CODE_COMMENT = 6;
    public static final int REQUEST_CODE_GET_SMS_CODE = 7;
    public static final int REQUEST_CODE_FORGET_PW = 8;
    public static final int REQUEST_CODE_MODIFIED_PW = 9;
    public static final int REQUEST_CODE_CHECK_VERSION = 10;
    public static final int REQUEST_CODE_CHECK_COUPON = 11; //验证优惠券,
    public static final int REQUEST_CODE_CHECK_PRODUCT = 12; //验证产品唯一码
    public static final int REQUEST_CODE_USE_COUPON = 13; //使用优惠券
    public static final int REQUEST_CODE_REGISTER = 14;  //粉丝注册升级
    public static final int REQUEST_CODE_REGISTER_CODE = 15; //获取粉丝注册新会员的短验码

    public static final int REQUEST_CODE_GET_LIKE = 0x10;  //获取经验点赞状态
    public static final int REQUEST_CODE_LIKE = 0x11;  //点赞
    public static final int REQUEST_CODE_UNLIKE = 0x12; //取消点赞

    public static final int REQUEST_CODE_SEARCH_MEMBER = 0x13; //搜索会员
    public static final int REQUEST_CODE_SEARCH_MEMBER_MORE = 0x14; //搜索更多会员
    public static final int REQUEST_CODE_MEMBER_DETIAL = 0x15; //会员详情
    public static final int REQUEST_CODE_MEMBER_GIFT_LISTL = 0x16; //会员礼物寄送列表
    public static final int REQUEST_CODE_MEMBER_GIFT_LISTL_MORE = 0x17; //会员礼物寄送列表
    public static final int REQUEST_CODE_MEMBER_GIFT_LISTL_DETIAL = 0x18; //会员礼物寄送列表
    public static final int REQUEST_CODE_IMPORT_SALE = 0x20; //重点热卖


    /**
     * 保存在SharedPreferences sToken 使用的key
     */
    public static final String KEY_DATA_TOKEN = "stoken";

    /**
     * 店长，如果sp 里面有这个key 就证明是店长
     */
    public static final String KEY_DATA_BOSS = "boss";

    public static final String KEY_DATA_ALLOWED_PHONE = "allowed_phone";
    /**
     * 保存在SharedPreferences username 使用的key
     */
    public static final String KEY_DATA_USERNAME = "username";
    /**
     * 保存在SharedPreferences homes 使用的key
     */
    public static final String KEY_DATA_HOMES = "homes";
    /**
     * 保存在SharedPreferences 是否有Homes 数据
     */
    public static final String KEY_DATA_HAS_HOMES = "hasHomes";
    /**
     * 保存在SharedPreferences members 使用的key
     */
    public static final String KEY_DATA_MEMBERS = "members";
    /**
     * 保存在SharedPreferences services 使用的key，waiting
     */
    public static final String KEY_DATA_SERVICES_WAITING = "servicesWaiting";
    /**
     * 保存在SharedPreferences services 使用的key，accept
     */
    public static final String KEY_DATA_SERVICES_ACCEPT = "servicesAccept";
    /**
     * 保存在SharedPreferences services 使用的key，complete
     */
    public static final String KEY_DATA_SERVICES_COMPLETE = "servicesComplete";
    /**
     * 保存在SharedPreferences store info 使用的key
     */
    public static final String KEY_DATA_STORE_INFO = "storeInfo";
    /**
     * 保存在SharedPreferences serviceCatalog 使用的key
     */
    public static final String KEY_DATA_SERVICE_CATALOG = "serviceCatalog";

    /**
     * 保存在本地 扫描的优惠信息 使用的key
     */
    public static final String KEY_DATA_SCAN_COUPON= "scanCoupon";
    /**
     * 保存在本地 扫描的产品信息 使用的key
     */
    public static final String KEY_DATA_SCAN_PRODUCT = "scanProduct";

    /**
     * 保存本地扫描的产品信息的json字符串，使用的key
     *
     */
    public static final String KEY_DATA_SCAN_PRODUCT_JSON = "scanProduct_json";

    /**
     * 上次下载Catalog 的时间戳
     */
    public static final String KEY_DATA_WHEN_DOWNLOAD_CATALOG = "when_download_catalog";
    /**
     * 保存在SharedPreferences store services 使用的key
     */
    public static final String KEY_DATA_STORE_SERVICES = "storeServices";
    /**
     * 保存在SharedPreferences store permission 使用的key
     */
    public static final String KEY_DATA_STORE_PERMISSION = "storePermission";
    /**
     * 保存在SharedPreferences store permission 使用的key
     */
    public static final String KEY_DATA_PRODUCT_TYPE = "productType";
    /**
     * 下载product的时间戳
     */
    public static final String KEY_DATA_WHEN_DOWNLOAD_PRODUCT_TYPE = "when_download_product_type";

    /**
     * 保存下载文件的是id
     */
    public static final String KEY_DATA_DOWNLOAD_ID = "download_id";
    /**
     * channel id
     */
    public static final String KEY_DATA_CHANNEL_ID = "channel_id";

    /**
     * First Use
     */
    public static final String APP_FIRST_START = "app_first_start";

    /**
     * 一页有多少条
     */
    public static final int NUM = 10;

    /**
     * 第1-5页
     */
    public static final int PAGE_HOME = 0;
    public static final int PAGE_VIP = 1;
    public static final int PAGE_ORDER = 2;
    public static final int PAGE_DATA = 3;
    public static final int PAGE_MARKET = 4;

    /**
     * 全部
     */
    public static final int EXCHANGE_STATE_ALL = -1;
    /**
     * 积分收入
     */
    public static final int EXCHANGE_STATE_IN = 1;
    /**
     * 积分支出
     */
    public static final int EXCHANGE_STATE_OUT = 0;
    /**
     * 优惠券未使用
     */
    public static final int EXCHANGE_STATE_NOT_USE = 0;
    /**
     * 优惠券使用了
     */
    public static final int EXCHANGE_STATE_USED = 1;

    //服务状态

    /**
     * 全部
     */
    public static final int SERVICE_STATE_ALL = -1;
    /**
     * 等待
     */
    public static final int SERVICE_STATE_WAITING = 0;
    /**
     * 接受
     */
    public static final int SERVICE_STATE_ACCEPT = 1;
    /**
     * 完成
     */
    public static final int SERVICE_STATE_COMPLETE = 2;
    /**
     * 扫描的优惠券或惠金币唯一码
     */
    public static  String  SCAN_COUPON_ONLY_CODE;
    /**
     * 扫描的优产品唯一码
     */
    public static  String  SCAN_PRODUCT_ONLY_CODE;

    /**
     * 保存到本地member_id
     */
    public static String FANS_MEMBER_ID;

}
