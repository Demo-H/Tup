package com.tupperware.huishengyi.http.api;

import com.tupperware.huishengyi.entity.saleenter.PostEnterBean;
import com.tupperware.huishengyi.entity.saleenter.ResponeBean;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;
import com.tupperware.huishengyi.entity.saleenter.SaleReportBean;
import com.tupperware.huishengyi.entity.saleenter.SaleTabBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dhunter on 2018/5/25.
 */

public interface ProductService {

    /**
     * 根据条形码获取记录信息
     * @param storeCode
     * @param date
     * @param barCode
     * @return
     */
    @GET("front/record/getByBarCode/{storeCode}/{date}/{barCode}")
    Observable<SaleEnterBean> getSaleScanData(@Path("storeCode") String storeCode, @Path("date") String date, @Path("barCode") String barCode);


    /**
     * 获取销售录入的tab标签
     * @return
     */

    @GET("front/record/product/series/")
    Observable<SaleTabBean> getSaleTabData();

    @GET("front/record/getBySeries/{storeCode}/{date}/{seriesId}")
    Observable<SaleEnterBean> getSaleEnterData(@Path("storeCode") String storeCode, @Path("date") String date, @Path("seriesId") String seriesId);

    @GET("front/record/getByDate/{storeCode}/{date}")
    Observable<SaleEnterBean> getSaleHistoryData(@Path("storeCode") String storeCode, @Path("date") String date);

    @GET("front/record/getBySearch/{storeCode}/{date}/{type}")
    Observable<SaleEnterBean> getSaleSearchData(@Path("storeCode") String storeCode, @Path("date") String date, @Path("type") int type,
                                                @Query("keyword") String keyword, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    @POST("front/record/add/")
    Observable<ResponeBean> postEnterListData(@Body PostEnterBean postEnterBean);

    @GET("front/record/saleReport/{storeCode}/{type}")
    Observable<SaleReportBean> getSaleReportData(@Path("storeCode") String storeCode, @Path("type") int type);
}
