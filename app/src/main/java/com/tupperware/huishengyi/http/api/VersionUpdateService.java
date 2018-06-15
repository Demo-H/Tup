package com.tupperware.huishengyi.http.api;

import com.tupperware.huishengyi.entity.home.VersionCheckRequest;
import com.tupperware.huishengyi.entity.home.VersionUpBean;

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

    @POST("front/version/version/check/")
    Observable<VersionUpBean> getDataResults(@Body VersionCheckRequest versionCheckRequest);
}
