package com.tupperware.biz.http.api;

import com.tupperware.biz.entity.BaseResponse;
import com.tupperware.biz.entity.VerifyCoupon;
import com.tupperware.biz.entity.VerifyProduct;
import com.tupperware.biz.entity.hotsale.HotInventoryResponse;
import com.tupperware.biz.entity.hotsale.HotSaleEnterReqeust;
import com.tupperware.biz.entity.login.ResponseBean;
import com.tupperware.biz.entity.saleenter.MemUpgradeRequest;
import com.tupperware.biz.entity.saleenter.PostEnterBean;
import com.tupperware.biz.entity.saleenter.ResponeBean;
import com.tupperware.biz.entity.saleenter.SaleEnterBean;
import com.tupperware.biz.entity.saleenter.SaleReportBean;
import com.tupperware.biz.entity.saleenter.SaleTabBean;

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
     * 券码核销
     */
    @GET("front/benefit/checkCoupon")
    Observable<VerifyCoupon> checkCoupon(@Query("qrCode") String qrCode);

    @POST("front/benefit/productunique/checkProductUniqueCode")
    Observable<VerifyProduct> verifyProductCode(@Body MemUpgradeRequest request);

    @POST("front/benefit/useCoupon")
    Observable<ResponseBean> useProductCode(@Body MemUpgradeRequest request);

    /**
     * 根据条形码获取记录信息
     * @param storeCode
     * @param date
     * @param barCode
     * @return
     */
    @GET("front/pos/record/getByBarCode/{storeCode}/{date}/{barCode}")
    Observable<SaleEnterBean> getSaleScanData(@Path("storeCode") String storeCode, @Path("date") String date, @Path("barCode") String barCode);


    /**
     * 获取销售录入的tab标签
     * @return
     */

    @GET("front/pos/record/product/series/")
    Observable<SaleTabBean> getSaleTabData();

    @GET("front/pos/record/getBySeries/{storeCode}/{date}/{seriesId}")
    Observable<SaleEnterBean> getSaleEnterData(@Path("storeCode") String storeCode, @Path("date") String date, @Path("seriesId") String seriesId);

    @GET("front/pos/record/getByDate/{storeCode}/{date}")
    Observable<SaleEnterBean> getSaleHistoryData(@Path("storeCode") String storeCode, @Path("date") String date);

    @GET("front/pos/record/getBySearch/{storeCode}/{date}/{type}")
    Observable<SaleEnterBean> getSaleSearchData(@Path("storeCode") String storeCode, @Path("date") String date, @Path("type") int type,
                                                @Query("keyword") String keyword, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    @POST("front/pos/record/add/")
    Observable<ResponeBean> postEnterListData(@Body PostEnterBean postEnterBean);

    @GET("front/pos/record/saleReport/{storeCode}/{type}")
    Observable<SaleReportBean> getSaleReportData(@Path("storeCode") String storeCode, @Path("type") int type);

    /** 重点热卖库存 **/
    @GET("front/hotsell/gethotsellStock")
    Observable<HotInventoryResponse> getHotSaleInventory(@Query("storeId") Integer storeId, @Query("time") String time);

    /** 重点热卖库存 **/
    @GET("front/hotsell/getHotsellList")
    Observable<HotInventoryResponse> getHotEnterList(@Query("storeId") Integer storeId);

    /** 重点热卖库存提交 **/
    @POST("front/hotsell/hotsellinput")
    Observable<BaseResponse> submitHotSale(@Body HotSaleEnterReqeust requestData);
}
