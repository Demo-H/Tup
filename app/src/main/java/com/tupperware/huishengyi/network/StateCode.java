package com.tupperware.huishengyi.network;

/**
 * Created by dhunter on 2018/2/9.
 */

public class StateCode {
    /**
     * 成功
     */
    public static final int STATE_SUCCESS = 200;
    /**
     * 账号或密码错误，未登录
     */
    public static final int STATE_PWD_NAME_FAULT = 530;
    /**
     * 账号或密码错误，未登录
     */
    public static final int STATE_NOT_LOGIN = 530;
    /**
     * 账号空
     */
    public static final int STATE_NAME_EMPTY = 202;
    /**
     * 参数不完整
     */
    public static final int STATE_PARAMETER_INCOMPLETE = 203;
    /**
     * 账号不存在 ,会员不存在
     */
    public static final int STATE_NAME_NOT_EXIST = 400;
    /**
     * 账号未启用
     */
    public static final int STATE_NAME_NOT_USE = 401;
    /**
     * 系统错误
     */
    public static final int STATE_SYSTEM_FAULT = 500;
    /**
     * 外部系统接入 ID或密码错误
     */
    public static final int STATE_OUT_PWD_NAME_FAULT = 1001;
    /**
     * 搜索结果为空
     */
    public static final int STATE_SEARCH_EMPTY = 30001;

    /**
     * 扫描验证不通过
     */
    public static final int STATE_VERIFY_ERROR = 300;

    /**
     * token过期
     */
    public static final int TOKEN_OUT_DATE = 104;
    public static final String TOKEN_OUT_DATE_S = "104";
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;
    /**
     * 证书出错
     */
    public static final int SSL_ERROR = 1005;

}
