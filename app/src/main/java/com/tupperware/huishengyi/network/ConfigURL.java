package com.tupperware.huishengyi.network;

/**
 * Created by dhunter on 2018/4/18.
 * 新接口
 */

public class ConfigURL {

//    public static final String BASE_URL = "http://192.168.10.170:8080/front";

    public static final String BASE_URL = "http://test.tbh.cn/front";
    /**
     * 登陆接口
     */
    public static final String LOGIN = BASE_URL + "/account/login";

    public static final String VERSION_CHECK = BASE_URL + "/version/version/check";

    /**
     * 首页头条
     */
    public static final String HOME_TOUTIAO_URL = BASE_URL + "/headline/news/index/";


    /**
     * 获取点赞状态
     */
    public static final String COLL_GET_LIKE = BASE_URL + "/school/answer/isLike/";

    /**
     * 点赞
     */
    public static final String COLL_SET_LIKE = BASE_URL + "/school/answer/like/";
    /**
     * 取消点赞
     */
    public static final String COLL_CANCEL_LIKE = BASE_URL + "/school/answer/unlike/";

}
