package com.tupperware.huishengyi.config;

import com.tupperware.huishengyi.utils.logutils.BundleParse;
import com.tupperware.huishengyi.utils.logutils.CollectionParse;
import com.tupperware.huishengyi.utils.logutils.IntentParse;
import com.tupperware.huishengyi.utils.logutils.MapParse;
import com.tupperware.huishengyi.utils.logutils.Parser;
import com.tupperware.huishengyi.utils.logutils.ReferenceParse;
import com.tupperware.huishengyi.utils.logutils.ThrowableParse;
import com.tupperware.huishengyi.utils.logutils.ParseList;

import java.util.List;

/**
 * Created by dhunter on 2018/3/5.
 */

public class Constant {

    public static final int TYPE_TOP_BANNER = 0xff01;
    public static final int TYPE_PRECISE_RECOMMENDATION = 0xff02;
    public static final int TYPE_ICON_LIST = 0xff03;
    public static final int TYPE_TARGET_ME = 0xff04;
    public static final int TYPE_MARKET_INFO = 0xff05;


    public static final int TYPE_VIP_PROJECT = 0xfe01;
    public static final int TYPE_MEMBER_MAINTENANCE = 0xfe02;
    public static final int TYPE_MEMBER_ATTRIBUTE = 0xfe03;
    public static final int TYPE_MEMBER_DATA = 0xfe04;
    public static final int TYPE_TITLE = 0xfe05;


    public static final int TYPE_ZHAOMU = 1;
    public static final int TYPE_ORDER = 2;
    public static final int TYPE_SWITCH_STORE = 3;
    public static final int TYPE_NO_TOP = 4;
    public static final int TYPE_STATUS = 5;


    public static final String COUPON_SCAN = "coupon_scan";
    public static final String PRODUCT_SCAN = "product_scan";

    //权限申请
    public static final int REQ_PERM_CAMERA = 1;

    public static final int CAMERA_WITH_DATA = 1;

    public static final String STRING_OBJECT_NULL = "Object[object is null]";

    // 解析属性最大层级
    public static final int MAX_CHILD_LEVEL = 2;

    public static final int MIN_STACK_OFFSET = 5;

    // 换行符
    public static final String BR = System.getProperty("line.separator");

    // 空格
    public static final String SPACE = "\t";

    // 默认支持解析库
    public static final Class<? extends Parser>[] DEFAULT_PARSE_CLASS = new Class[]{
            BundleParse.class, IntentParse.class, CollectionParse.class,
            MapParse.class, ThrowableParse.class, ReferenceParse.class
    };


    public static final boolean DemoTest = true;
    public static final boolean Demo = true;
    public static final boolean DemoF = false;

    /**
     * 获取默认解析类
     *
     * @return
     */
    public static final List<Parser> getParsers() {
        return ParseList.getInstance().getParseList();
    }

    public static final String ACTIVITY_CREATE_FROM = "From";
    public static final String ACTIVITY_TITLE = "Title";
    public static final String FRAGMENT_TAB_POSITION = "POSITION";
    public static final String FRAGMENT_FLAG = "Flag";
    public static final String MODULT_FROM = "module_from";

    public static final int MENU_TABLE_ITEM_COUNT_2 = 2;
    public static final int MENU_TABLE_ITEM_COUNT_3 = 3;
    public static final int MENU_TABLE_ITEM_COUNT_4 = 4;
    public static final int MENU_TABLE_ITEM_COUNT_5 = 5;
    public static final int MENU_TABLE_ITEM_COUNT_6 = 6;

    public static final String COUPON = "coupon";
    public static final String BENEFIT = "benefit";
    public static final String NEW_ADD = "new_add";
    public static final String MODIFIED = "modified";
    public static final String HOME = "Home";
    public static final String PERSONAL_INFO = "PersonalInfo";
    public static final String ENTER_PRODUCT = "enter_product";
    public static final String REGISTER_CHOOSE = "register_choose";
    public static final String KEY_SALE_PROJECT = "key_sale_project";
    public static final String WATER_SAFE = "water_safe";
    public static final String PERSONAL_TAILOR = "personal_tailor";
    public static final String CARNIVAL = "carnival";
    public static final String RESERVATION_QR = "reservation_qr";
    public static final String FILL_ADDR = "fill_addr";
    public static final String PORPUSE_FOLLOW = "porpuse_follow";
    public static final String SERVER_MANAGER = "server_manager";
    public static final String ORDER_FRAGMENT = "order_fragment";
    public static final String LOVE_VIP_FRAGMENT = "love_vip_fragment";
    public static final String PERSONAL_SETTING = "personal_setting";
    public static final String MEMBER_DETIAL = "member_detial";

    /**
     * 登录和忘记密码
     */
    public static final String FORGET_INFO = "forget_info";

    /**
     *券码核销
     */
    public static final String ISVERIFY = "isverify";
    public static final int AFTER_SCAN_SUCCESS = 1;
    public static final int CAN_NOT_SCAN = 2;

    /**
     * 销售录入
     */
    public static final String PRODUCT_PROVIDER = "product_provider";
    public static final String PRODUCT_HISTORY_PROVIDER = "product_history_provider";
    public static final String SELECT_DATE = "select_date";
    public static final String SELECT_SCAN_TYPE = "select_scan_type";
    public static final int REQUEST_INTENT_SEARCH_CODE = 0x20;

    /**
     * 会员
     */
    public static final String MEMBER_ID = "member_id";
    public static final String MEMBER_PHONE = "member_phone";
    public static final String REMARKS_CONTENT = "remarks_content";
    public static final String MEMBER_GIFT_DATA = "member_gift_data";
    public static final int REQUEST_REMARKS_CONTENT = 0;
    public static final int REQUEST_REMARKS_CONTENT_RESULT = 2;
    public static final String MEMBER_REPORT_DATA = "member_report_data";
    public static final String MEMBER_ADD_NEW_DATA = "member_add_new_data";
    public static final String ACTION_MEMBER_INFO_ID = "action_member_info_id";
    public static final String ACTION_MEMBER_STORE_ID = "action_member_store_id";
    public static final String ACTION_MEMBER_ID = "action_member_id";

    /**
     * 消息
     */
    public static final String MSG_TYPE = "msg_type";
    public static final String ORDER_REMINDING = "order_reminding";
    public static final String ZIXUN_UPDATE = "zixun_update";
    public static final String Sys_MSG = "sys_msg";
    public static final String MSG_TITLE = "msg_title";
    public static final int ENTER_PRODUCT_SCAN = 3;
    public static final String MSG_RED_TIP = "msg_red_tip";



    //日期
    public static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "extra_date";
    public static final int REQUEST_DATE = 0;
    public static final String DIALOG_DATE = "DialogDate";

    //注册会员
    public static final String POSITION = "position";

    //订单
    /**
     * WAIT_PAY("待付款", 0),
     * WAIT_DELIVERY("待发货", 1),
     * HAD_DELIVERY("已发货", 2),
     * REFUNDING("退款中", 3),
     * DONE("已完成", 4),
     * CLOSED("已关闭", 5),
     * RECEIVED("已收货", 6),
     * START_REFUND("发起退款", 7);
     */
    public static final int WAIT_PAY = 0 ;
    public static final int WAIT_SEND = 1 ;
    public static final int SENDED = 2 ;
    public static final int COMPLETED = 4;
    public static final int ROLLBACK = 3;
    public static final int CLOSED = 5;
    public static final String ORDER_ID = "order_id";

    //数据窗
    public static final String SINGLE_VIP_COUNT_PRECENT = "single_vip_count_precent";
    public static final String SINGLE_VIP_STATUS = "single_vip_status";
    public static final String SINGLE_PRODUCT_TOP = "single_product_top10";
    public static final String SINGLE_PRODUCT_SALE_TOP = "single_product_sale_top10";
    public static final String SINGLE_SALE_ATTRIBUTE = "single_sale_attribute";
    public static final String SINGLE_MAIN_PRODUCT = "single_main_product";
    public static final String STORE_SALE_MONEY_LIST = "store_sale_money_list";
    public static final String STORE_SALE_LIST = "store_sale_list";
    public static final String STORE_SALE_ANALYSIS = "store_sale_analysis";
    public static final String STORE_VIP_SALE_PRECENT = "store_vip_sale_precent";
    public static final String STORE_VIP_STATUS_ANALYSIS = "store_vip_status_analysis";
    public static final String STORE_SALE_ATTRIBUTE = "store_sale_attribute";
    public static final String PRODUCT_SALE_TOP = "product_sale_top10";
    public static final String PRODUCT_SALE_MONEY_TOP = "product_sale_money_top10";
    public static final String MAIN_PRODUCT_LIST = "main_product_list";

    /**
     * 分页
     */
    public static final int DEFAULT_PAGE_INDEX = 0;
    public static final int FIRST_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE = 5;
    public static final int DEFAULT_MEMBER_PAGE_SIZE = 10;

    /**
     * 网页
     */
    public static final String OPEN_URL = "url";
    public static final String URL_TITLE = "title_name";
    public static final String URL_TYPE = "url_type";
    public static final String SUPPORT_LIKE = "support_like";
    public static final String ANSWER_ID = "answer_id";
    public static final String LIKE_COUNT = "like_count";
    public static final String MSG_TAG_ID = "msg_id";
    public static final String NEED_COOKIE = "need_cookie";

    /**
     * 精品课程
     */
    public static final String COURSE_ID = "course_id";

    /**
     * 发展新会员
     */
    public static final String DEV_MEMBER_DATA = "dev_member_data";
    public static final String DEV_MEMBER_RQ_CODE = "dev_member_qr_code";
    public static final String DEV_MEMBER_PHONE = "dev_member_phone";
    public static final String TAG_CODE_LIKE = "0101";
    public static final String TAG_CODE_NOT_LIKE = "0102";
    public static final String TAG_CODE_WANT_STUDY = "0201";
    public static final String TAG_CODE_NOT_WANT_STUDY = "0202";
    public static final String TAG_CODE_TASTE_1= "0301";  //无辣不欢
    public static final String TAG_CODE_TASTE_2= "0302";  //清淡
    public static final String TAG_CODE_TASTE_3= "0303";  //酸甜
    public static final String TAG_CODE_TASTE_4= "0304";  //中餐胃
    public static final String TAG_CODE_TASTE_5= "0305";  //西餐粉
    public static final String TAG_CODE_TASTE_6= "0306";  //肉食动物
    public static final String TAG_CODE_TASTE_7= "0307";  //素食主义
    public static final String TAG_CODE_TASTE_8= "0308";  //甜品控
    public static final String TAG_CODE_HAVE_WATER= "0401";  //有饮水器
    public static final String TAG_CODE_NOT_HAVE_WATER= "0402";  //没有饮水器
    public static final String TAG_CODE_CLEAN= "0501";
    public static final String TAG_CODE_NOT_CLEAN= "0502";
    public static final String TAG_CODE_SINGER= "0601";
    public static final String TAG_CODE_LOVE= "0602";
    public static final String TAG_CODE_CHILD= "0603";
    public static final String TAG_CODE_FAMILY= "0604";
    public static final String TAG_CODE_GENERAL= "0701";  //工薪阶级（3000-10000）
    public static final String TAG_CODE_SMALL_RICH= "0702";  //小资白领（10000-30000）
    public static final String TAG_CODE_MIDDLE_RICH= "0703";  //中产（公务员/教师）（8000-20000）
    public static final String TAG_CODE_VERY_RICH= "0704";  //富豪（企业主）（3万以上）
    public static final String TAG_CODE_WATER_CLEANER = "0801";  //净水器系列
    public static final String TAG_CODE_TUP = "0802";  //TUP系列
    public static final String TAG_CODE_TAKE_IN = "0803";  //收纳系列
    public static final String TAG_CODE_KITCHNEN = "0804";  //厨具系列


}
