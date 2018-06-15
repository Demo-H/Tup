package com.tupperware.huishengyi.network;

/**
 * Created by dhunter on 2018/2/1.
 * 存放服务器的url
 */

public class ServerURL {
    /**
     * 主机，域名
     */
//    public static final String HOST_PREFIX = "http://msstest.tbh.cn/";
//    public static final String HOST_PREFIX = "http://www.tbh.cn/";
    public static final String HOST_PREFIX = "https://test.tbh.cn/";
//    public static final String HOST_PREFIX = "http://tbh.bamboonetworks.com/";
//    public static final String HOST_PREFIX = "http://t.tbh.cn/";
    public static String URL_IMG = "http://img.tbh.cn/";

    /**
     * url 前缀
     */
    private static final String URL_PREFIX = HOST_PREFIX + "store_api";
    private static final String URL_MEMBER_PREFIX = HOST_PREFIX + "member_api";


    /**
     * 登陆接口
     */
    public static final String LOGIN = URL_PREFIX + "/app.php?act=store_log_in";
    /**
     * 退出接口 *
     */
    public static final String LOGOUT = URL_PREFIX + "/app.php?act=store_log_out";
    /**
     * 登陆主页接口 *
     */
    public static final String HOME = URL_PREFIX + "/app.php?act=store_index";

    /**
     * 会员列表接口
     */
    public static final String MEMBER_LIST = URL_PREFIX + "/app.php?act=memberList";

    /**
     * 会员预约服务接口
     */
    public static final String MEMBER_RESERVATION = URL_PREFIX + "/Member.php?act=member_reservation_records";

    /**
     * 会员礼物进度列表
     */
    public static final String MEMBER_GIFT_LIST = URL_PREFIX + "/app.php?act=getMemberGiftOrder";

    /**
     * 会员礼物进度列表详情
     */
    public static final String MEMBER_GIFT_LIST_DETIAL = URL_PREFIX + "/app.php?act=getMemberGiftOrderDetail";

    /**
     * 会员详情接口
     */
    public static final String MEMBER_INFO = URL_PREFIX + "/app.php?act=memberDetail";
    /**
     * 会员备注保存
     */
    public static final String MEMBER_REMARK_SAVE = URL_PREFIX + "/Member.php?act=remark_save";

    /**
     * 会员购物记录接口
     */
    public static final String MEMBER_PURCHASE_RECORDS = URL_PREFIX + "/Member.php?act= member_purchase_records";

    /**
     * 专卖店预约服务接口
     */
    public static final String MEMBER_PURCHASE_RECORDS_X = URL_PREFIX + "/Member.php?act=member_purchase_records";

    /**
     * 专卖店服务
     */
    public static final String STORE_RESERVATION_LIST = URL_PREFIX + "/app.php?act=store_reservation_list";

    /**
     * 预约服务类别接口
     */
    public static final String SERVICE_RESERVATION_CATALOG = URL_PREFIX + "/app.php?act=store_reservation_catalog";

    /**
     * 接受服务处理
     */
    public static final String RESERVATION_ACCEPT = URL_PREFIX + "/app.php?act=reservation_accept";

    /**
     * 服务处理接口
     */
    public static final String RESERVATION_COMPLETE = URL_PREFIX + "/app.php?act=reservation_processed";

    /**
     * 专卖店信息接口
     */
    public static final String STORE_INFO = URL_PREFIX + "/app.php?act=store_info";
    /**
     * 专卖店信息编辑接口
     */
    public static final String STORE_EDIT = URL_PREFIX + "/app.php?act=store_edit";

    /**
     * 优惠券
     */
    public static final String STORE_COUPON = URL_PREFIX + "/app.php?act=store_coupon";

    /**
     * 积分
     */
    public static final String STORE_INTEGRAL = URL_PREFIX + "/app.php?act=store_integral";

    /**
     * 产品类别
     */
    public static final String PRODUCT_CATEGORIES = URL_PREFIX + "/app.php?act=product_categories";

    /**
     * 用户反馈
     */
    public static final String MEMBER_FEEDBACK = URL_PREFIX + "/app.php?act=member_feedback";

    /**
     * 验证优惠券唯一码
     */
    public static final String VEFIRY_COUPON = URL_PREFIX + "/app.php?act=check_coupon";
    /**
     * 验证惠金币唯一码
     */
    public static final String VEFIRY_POINT = URL_PREFIX + "/app.php?act=check_integral";
    /**
     * 验证产品唯一码
     */
    public static final String VEFIRY_PRODUCT = URL_PREFIX + "/app.php?act=checkProductUniqueCode";  //相比1.0接口有改动
    /**
     * 提交和使用
     */
    public static final String USE_COUPONS = URL_PREFIX + "/app.php?act=useProductCode"; //相比1.0接口有改动

    /**
     * 店员列表
     */
    public static final String SALESMAN_LIST = URL_PREFIX + "/app.php?act=get_store_employee_list";

    /**
     * 新增店员
     */
    public static final String SALESMAN_ADD = URL_PREFIX + "/app.php?act=add_store_employee";

    /**
     * 更新店员
     */
    public static final String SALESMAN_UPDATE = URL_PREFIX + "/app.php?act=edit_store_employee";

    /**
     * 删除店员
     */
    public static final String SALESMAN_CHANGE_STATUS = URL_PREFIX + "/app.php?act=change_store_employee_status";

    /**
     * 获取验证码http://tbh.bamboonetworks.com/member_api/member.php?act=send_valid_code&mobile=13428283636
     */
    public static final String SEND_VALID_CODE = URL_MEMBER_PREFIX + "/member.php?act=send_valid_code";

    /**
     * 忘记密码获取手机号码
     */
    public static final String GET_EMPLOYEE_MOBILE = URL_PREFIX + "/app.php?act=getEmployeeMobile";

    /**
     * 确认验证码
     */
    public static final String CHECK_CODE = URL_MEMBER_PREFIX + "/member.php?act=check_code";

    /**
     * 重设密码（忘记密码）
     */
    public static final String FIND_PASSWORD = URL_PREFIX + "/app.php?act=find_password";
    /**
     * 修改密码
     */
    public static final String CHANGE_PWD = URL_PREFIX + "/app.php?act=updatePassword";

    /**
     * 关于
     */
    public static final String STORE_ABOUT = HOST_PREFIX + "store_about.html";

    /**
     * 帮助
     */
    public static final String STORE_HELP = HOST_PREFIX + "store_help.html";

    /**
     * 版本检测 {
     * "code": 200,
     * "url": "http://",
     * "version": "0.06"
     * }
     */
    public static final String CHECK_VERSION = HOST_PREFIX + "app/huishengyi/check_android_version.json";

    /**
     * 会员礼品寄送进度
     */
    public static final String STORE_ORDER_LIST = HOST_PREFIX + "wechat/index.php?app=gift&act=store_order_list";

    /**
     * 积分明细
     */
    public static final String POINT_LIST = URL_PREFIX + "";

    /**
     * 粉丝注册会员获取的验证码
     */
    public static final String GET_REGISTER_SMS_CODE = URL_PREFIX + "/app.php?act=sendValidateCode";


    /**
     * 粉丝注册会员绑定手机号码接口
     */
    public static final String BIND_PHONE_NUMBER = URL_MEMBER_PREFIX + "/member.php?act=bind_mobile";

    /**
     * 粉丝升级会员接口
     */
    public static final String FANS_UPGRADE_MEMBER = URL_PREFIX + "/app.php?act=memberUpGrade";

    /**
     * 重点热卖外链接口
     */
    public static final String IMPORTANT_PRODUCT_SALE = "http://r.tbh.cn/index.php?s=/Api/WapLoginVerify/I_autoLogin";

    /**
     * 重点热卖外链接口(测试)
     */
    public static final String IMPORTANT_PRODUCT_SALE_TEST = "http://rx.qijian360.com/index.php?s=/Api/WapLoginVerify/I_autoLogin";

    public static final String TEST_URL = "https://test.tbh.cn/store_api/app.php?act=remaiUrl";
}
