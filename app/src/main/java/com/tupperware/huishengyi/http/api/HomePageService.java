package com.tupperware.huishengyi.http.api;

import com.tupperware.huishengyi.entity.college.ConditionRequest;
import com.tupperware.huishengyi.entity.home.HomeBean;
import com.tupperware.huishengyi.entity.msg.MsgBean;
import com.tupperware.huishengyi.entity.msg.MsgItemBean;
import com.tupperware.huishengyi.entity.msg.MsgRedTip;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by dhunter on 2018/4/20.
 */

public interface HomePageService {

    @GET("front/index/")
    Observable<HomeBean> getDataResults();

    /**
     * 获取消息分类
     * @return
     */
    @POST("front/message/tag/myList/")
    Observable<MsgBean> getMsgData();

    /**
     * 获取消息详细列表
     * @return
     */
    @POST("front/message/list/")
    Observable<MsgItemBean> getMsgItemData(@Body ConditionRequest conditionRequest);

    /**
     * 消息未读提示
     * @return
     */
    @POST("front/common/polling/")
    Observable<MsgRedTip> getMsgRedTipData();

}
