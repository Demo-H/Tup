package com.tupperware.biz.http.api;

import com.tupperware.biz.entity.home.VersionCheckRequest;
import com.tupperware.biz.entity.home.VersionUpBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dhunter on 2018/4/25.
 */

public interface VersionUpdateService {

//    @FormUrlEncoded
//    @POST("version/version/check/")
//    Observable<ResponseBody> getDataResults(@Field("userId")String userId, @Field("version")String version);

    @POST("front/common/version/check/")
    Observable<VersionUpBean> getDataResults(@Body VersionCheckRequest versionCheckRequest);
}
