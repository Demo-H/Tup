package com.tupperware.huishengyi.http.api;

import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.entity.college.ConditionRequest;
import com.tupperware.huishengyi.entity.college.CollegeTabBean;
import com.tupperware.huishengyi.entity.college.CourseBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by dhunter on 2018/5/2.
 */

public interface CollegeService {

    /**
     * 获取商学院的tab标签
     * @param conditionRequest
     * @return
     */

    @POST("front/school/tag/list/")
    Observable<CollegeTabBean> getCollegeTabData(@Body ConditionRequest conditionRequest);

    /**
     * 获取精选资讯
     * @param conditionRequest
     * @return
     */
    @POST("front/school/text/front/")
    Observable<CollegeBean> getArticleData(@Body ConditionRequest conditionRequest);

    @POST("front/school/answer/front/")
    Observable<CollegeBean> getExperienceData(@Body ConditionRequest conditionRequest);

    @POST("front/school/course/front/")
    Observable<CollegeBean> getCourseData(@Body ConditionRequest conditionRequest);

    @GET("front/school/ad/getCourse/")
    Observable<CollegeBean> getAdvertData();

    @GET("front/school/course/get/{id}")
    Observable<CourseBean> getCourDetialData(@Path("id") long tagId);
}
