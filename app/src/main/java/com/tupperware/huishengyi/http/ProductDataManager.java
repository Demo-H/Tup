package com.tupperware.huishengyi.http;

import com.android.dhunter.common.network.DataManager;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.config.NetWorkUrl;
import com.tupperware.huishengyi.entity.VerifyCoupon;
import com.tupperware.huishengyi.entity.VerifyProduct;
import com.tupperware.huishengyi.entity.college.ConditionRequest;
import com.tupperware.huishengyi.entity.login.ResponseBean;
import com.tupperware.huishengyi.entity.saleenter.MemUpgradeRequest;
import com.tupperware.huishengyi.entity.saleenter.PostEnterBean;
import com.tupperware.huishengyi.entity.saleenter.ResponeBean;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterBean;
import com.tupperware.huishengyi.entity.saleenter.SaleReportBean;
import com.tupperware.huishengyi.entity.saleenter.SaleTabBean;
import com.tupperware.huishengyi.http.api.ProductService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * Created by dhunter on 2018/5/25.
 */

public class ProductDataManager extends BaseDataManager {

    public ProductDataManager(DataManager mDataManager) {
        super(mDataManager);
    }

    public static ProductDataManager getInstance(DataManager dataManager){
        return new ProductDataManager(dataManager);
    }

    /**券码核销-- 优惠券检查 **/
    public Disposable checkCoupon(DisposableObserver<VerifyCoupon> consumer, String qrCode) {
        Observable observable = getService(ProductService.class).checkCoupon(NetWorkUrl.CHECK_COUPON, getCheckCouponRequest(qrCode));
        return changeIOToMainThread(observable, consumer);
    }

    /**券码核销-- 产品唯一码检查检查 **/
    public Disposable verifyProductCode(DisposableObserver<VerifyProduct> consumer, String qrCode, String uniqueCode) {
        Observable observable = getService(ProductService.class).verifyProductCode(NetWorkUrl.VERIFY_PRODUCT, getProductRequest(qrCode, uniqueCode));
        return changeIOToMainThread(observable, consumer);
    }

    /**券码核销-- 产品唯一码检查检查 **/
    public Disposable useProductCode(DisposableObserver<ResponseBean> consumer, String qrCode, String uniqueCode) {
        Observable observable = getService(ProductService.class).useProductCode(NetWorkUrl.USE_PRODUCT_CODE, getProductRequest(qrCode, uniqueCode));
        return changeIOToMainThread(observable, consumer);
    }


    /**
     * 根据条形码获取记录信息
     * get
     */
    public Disposable getSaleScanData(DisposableObserver<SaleEnterBean> consumer, String storeCode, String date, String barCode) {
        Observable observable = getService(ProductService.class).getSaleScanData(storeCode, date, barCode);
        Observable observableCahce = providers.getSaleScanTypes(observable, new DynamicKey("salescan"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<SaleEnterBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 销售手动录入tab种类
     * @param consumer
     * Get
     * @return
     */
    public Disposable getSaleTabData(DisposableObserver<SaleTabBean> consumer) {

        Observable observable = getService(ProductService.class).getSaleTabData();
        Observable observableCahce = providers.geSaleTabTypes(observable, new DynamicKey("saleTab"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<SaleTabBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 销售手动录入数据
     * @param consumer
     * Get
     * @return
     */
    public Disposable getSaleEnterData(DisposableObserver<SaleEnterBean> consumer, String storeCode, String date, String seriesId) {
        Observable observable = getService(ProductService.class).getSaleEnterData(storeCode, date, seriesId);
        Observable observableCahce = providers.getSaleEnterTypes(observable, new DynamicKey("saleenter"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<SaleEnterBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 销售历史数据
     * @param consumer
     * @return
     */
    public Disposable getSaleHistoryData(DisposableObserver<SaleEnterBean> consumer, String storeCode, String date) {
        Observable observable = getService(ProductService.class).getSaleHistoryData(storeCode, date);
        Observable observableCahce = providers.getSaleHistoryTypes(observable, new DynamicKey("salehistory"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<SaleEnterBean>>());
        return changeIOToMainThread(observableCahce, consumer);
    }

    /**
     * 录入搜索查询
     * @param consumer
     * @return
     */
    public Disposable getProductSearchData(DisposableObserver<SaleEnterBean> consumer, String storeCode, String date, int type, String keyword, int pageIndex) {
        Observable observable = getService(ProductService.class)
                .getSaleSearchData(storeCode, date, type, keyword, pageIndex, Constant.DEFAULT_MEMBER_PAGE_SIZE);
        return changeIOToMainThread(observable, consumer);
    }

    /**
     * 提交清单
     * @return
     */
    public Disposable postEnterListData(DisposableObserver<ResponeBean> consumer, PostEnterBean postEnterBean) {
        Observable observable = getService(ProductService.class)
                .postEnterListData(postEnterBean);
        return changeIOToMainThread(observable, consumer);
    }

    public Disposable getSaleReportData(DisposableObserver<SaleReportBean> consumer, String storeCode, int type) {
        Observable observable = getService(ProductService.class).getSaleReportData(storeCode, type);
        Observable observableCahce = providers.getSaleReportTypes(observable, new DynamicKey("salereport"), new EvictDynamicKey(true)).map(new HttpResultFuncCache<List<SaleReportBean>>());
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
        return request;
    }

    private ConditionRequest getRequest(String code, String status, int pageIndex) {
        ConditionRequest request = new ConditionRequest();
        ConditionRequest.Condition condition = new ConditionRequest.Condition();
        condition.setCode(code);
        condition.setStatus(status);
        request.setCondition(condition);
        ConditionRequest.PageQuery query = new ConditionRequest.PageQuery();
        query.setPageIndex(pageIndex);
        query.setPageSize(Constant.DEFAULT_PAGE_SIZE);
//        query.setTotalRows(0);
        request.setPagingQuery(query);
        return request;
    }

    private MemUpgradeRequest getCheckCouponRequest(String qrCode) {
        MemUpgradeRequest request = new MemUpgradeRequest();
        request.setQrCode(qrCode);
        return request;
    }

    private MemUpgradeRequest getProductRequest(String qrCode, String uniqueCode) {
        MemUpgradeRequest request = new MemUpgradeRequest();
        request.setQrCode(qrCode);
        request.setUniqueCode(uniqueCode);
        return request;
    }
}
