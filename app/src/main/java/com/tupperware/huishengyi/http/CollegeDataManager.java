package com.tupperware.huishengyi.http;

import com.android.dhunter.common.network.DataManager;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.college.CollegeBean;
import com.tupperware.huishengyi.entity.college.ConditionRequest;
import com.tupperware.huishengyi.entity.college.CollegeTabBean;
import com.tupperware.huishengyi.entity.college.CourseBean;
import com.tupperware.huishengyi.entity.college.LikeBean;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.http.api.CollegeService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * Created by dhunter on 2018/5/2.
 */

public class CollegeDataManager extends BaseDataManager {

    public CollegeDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static CollegeDataManager getInstance(DataManager dataManager){
        return new CollegeDataManager(dataManager);
    }


    public Disposable getCollegeTabData(DisposableObserver<CollegeTabBean> consumer) {

        Observable observable = getService(CollegeService.class).getCollegeTabData(getRequest(Constant.DEFAULT_PAGE_INDEX,Constant.DEFAULT_PAGE_INDEX));
        Observable observableCahce = providers.getCollegeTabTypes(observable, new DynamicKey("collegeTab"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<CollegeTabBean>>());

        return changeIOToMainThread(observableCahce, consumer);
    }

    public Disposable getArticleData(DisposableObserver<CollegeBean> consumer, int tagId) {

        Observable observable = getService(CollegeService.class).getArticleData(getRequest(tagId, Constant.FIRST_PAGE_INDEX));
        Observable observableCahce = providers.getArticleTypes(observable, new DynamicKey("college_article"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<CollegeBean>>());

        return changeIOToMainThread(observableCahce, consumer);
    }

    public Disposable getMoreArticleData(DisposableObserver<CollegeBean> consumer, int tagId, int pageIndex) {
        //不缓存
        Observable observable = getService(CollegeService.class).getArticleData(getRequest(tagId,pageIndex));
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable getExperienceData(DisposableObserver<CollegeBean> consumer, int tagId) {
        Observable observable = getService(CollegeService.class).getExperienceData(getRequest(tagId, Constant.FIRST_PAGE_INDEX));
        Observable observableCahce = providers.getExperienceTypes(observable, new DynamicKey("college_experience"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<CollegeBean>>());

        return changeIOToMainThread(observableCahce, consumer);
    }

    public Disposable getMoreExperienceData(DisposableObserver<CollegeBean> consumer, int tagId, int pageIndex) {
        //不缓存
        Observable observable = getService(CollegeService.class).getExperienceData(getRequest(tagId,pageIndex));
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable getCourseData(DisposableObserver<CollegeBean> consumer, int tagId) {
        Observable observable = getService(CollegeService.class).getCourseData(getRequest(tagId, Constant.FIRST_PAGE_INDEX));
        Observable observableCahce = providers.getCourseTypes(observable, new DynamicKey("college_course"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<CollegeBean>>());

        return changeIOToMainThread(observableCahce, consumer);
    }

    public Disposable getMoreCourseData(DisposableObserver<CollegeBean> consumer, int tagId, int pageIndex) {
        //不缓存
        Observable observable = getService(CollegeService.class).getCourseData(getRequest(tagId,pageIndex));
        return changeIOToMainThread(observable, consumer);
    }

    //get
    public Disposable getAdvertData(DisposableObserver<CollegeBean> consumer) {
        Observable observable = getService(CollegeService.class).getAdvertData();
        Observable observableCahce = providers.getAdvertTypes(observable, new DynamicKey("college_advert"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<CollegeBean>>());

        return changeIOToMainThread(observableCahce, consumer);
    }

    public Disposable getIsLike(DisposableObserver<LikeBean> consumer, long answerId) {
        Observable observable = getService(CollegeService.class).getIsLike(answerId);
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable setLike(DisposableObserver<ResponseBean> consumer, long answerId) {
        Observable observable = getService(CollegeService.class).setLike(answerId);
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable cancelLike(DisposableObserver<ResponseBean> consumer, long answerId) {
        Observable observable = getService(CollegeService.class).cancelLike(answerId);
        return changeIOToMainThread(observable, consumer);
    }


    /**
     * get 获取课程详情
     * @param consumer
     * @return
     */
    public Disposable getCourDetialData(DisposableObserver<CourseBean> consumer, long tagId) {
        Observable observable = getService(CollegeService.class).getCourDetialData(tagId);
        Observable observableCahce = providers.getCourDetialTypes(observable, new DynamicKey("course_detial"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<CourseBean>>());

        return changeIOToMainThread(observableCahce, consumer);
    }

    private ConditionRequest getRequest(int tagId, int pageIndex) {
        ConditionRequest request = new ConditionRequest();
        ConditionRequest.Condition condition = new ConditionRequest.Condition();
        condition.setTagId(tagId);
        request.setCondition(condition);
        ConditionRequest.PageQuery query = new ConditionRequest.PageQuery();
        query.setPageIndex(pageIndex);
        if(tagId == Constant.DEFAULT_PAGE_INDEX) {
            query.setPageSize(Constant.DEFAULT_PAGE_INDEX);
        } else {
            query.setPageSize(Constant.DEFAULT_PAGE_SIZE);
        }
//        query.setTotalRows(0);
        request.setPagingQuery(query);
        ConditionRequest.TimelineQuery timelineQuery = new ConditionRequest.TimelineQuery();
        timelineQuery.setTimestamp(Constant.DEFAULT_PAGE_INDEX);
        timelineQuery.setPageSize(Constant.DEFAULT_PAGE_INDEX);
        return request;
    }
}
