package com.tupperware.huishengyi.config;

/**
 * Created by dhunter on 2018/7/6.
 */

public class NetWorkUrl {

    public static final String LOGIN_URL = "store_log_in";
    public static final String LOGOUT_URL = "store_log_out";

    public static final String GET_PHONE_BY_USERNAME = "getEmployeeMobile"; //找回密码,获取手机号码
    public static final String GET_VALIDATE_CODE = "sendValidateCode"; //   发送获取验证码
    public static final String FIND_PASSWORD = "find_password";

    public static final String MODFIED_PASSWORD = "updatePassword"; //修改密码

    public static final String MEMBER_SEARCH_LIST = "memberList"; // 搜索获取会员列表
    public static final String MEMBER_DETIAL = "memberDetail"; //会员详细接口
    public static final String MEMBER_GIFT_LIST = "getMemberGiftOrder"; //会员礼品订单列表

    public static final String CHECK_COUPON = "check_coupon";
    public static final String VERIFY_PRODUCT = "checkProductUniqueCode";
    public static final String USE_PRODUCT_CODE = "useProductCode";
    public static final String MEMBER_UPGRADE = "memberUpGrade";

}
