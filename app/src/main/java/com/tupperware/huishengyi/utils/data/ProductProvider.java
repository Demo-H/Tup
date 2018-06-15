package com.tupperware.huishengyi.utils.data;

import android.content.Context;

import com.android.dhunter.common.utils.SharePreferenceData;
import com.google.gson.reflect.TypeToken;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.saleenter.SaleEnterContent;
import com.tupperware.huishengyi.utils.ObjectUtil;
import com.tupperware.huishengyi.utils.logutils.LogF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dhunter on 2018/5/29.
 */

public class ProductProvider {

    private static final String TAG = "ProductProvider";
//    private SparseArray<SaleEnterContent> datas = null ;
    private HashMap<String, SaleEnterContent> mapDatas = null;
    private Context context = null ;
    private static ProductProvider mInstance ;
    private SharePreferenceData mSharePreDate;

    private ProductProvider(Context context) {
        this.context = context ;
//        this.datas = new SparseArray<>(10) ;
        this.mapDatas = new HashMap<>();
        mSharePreDate = new SharePreferenceData(context);
        listToSparseArray() ;
    }

    public static synchronized ProductProvider getInstance(Context context){
        if(mInstance == null){
            mInstance = new ProductProvider(context);
        }
        return mInstance;
    }

    public void put (SaleEnterContent mBean){

//        SaleEnterContent temp = datas.get(StringUtils.StringChangeToInt(mBean.getCode())) ;
        SaleEnterContent temp = mapDatas.get(mBean.getCode()) ;
        LogF.d("------ ", " "+mBean.getCode()) ;

        if (temp != null){
            temp.setLocalStockNum(mBean.getLocalStockNum());
            temp.setLocalSaleNum(mBean.getLocalSaleNum());
        }else {
            temp = mBean ;
        }
//        datas.put(StringUtils.StringChangeToInt(mBean.getCode()), temp);
        mapDatas.put(mBean.getCode(), temp);
        commit() ;
    }


//    public void put (SaleEnterContent mBean){
//        this.put(mBean);
//    }

    public boolean isExitList(SaleEnterContent mBean) {
//        SaleEnterContent temp = datas.get(StringUtils.StringChangeToInt(mBean.getCode()));
        SaleEnterContent temp = mapDatas.get(mBean.getCode());
        if(temp != null) {
            //同步本地数据
            mBean.setLocalStockNum(temp.getLocalStockNum());
            mBean.setLocalSaleNum(temp.getLocalSaleNum());
            return true;
        } else {
            return false;
        }
    }

    public int getListSize() {
//        return datas.size();
        return mapDatas.size();
    }

    public void update (SaleEnterContent mBean){
//        datas.put(StringUtils.StringChangeToInt(mBean.getCode()), mBean);
        mapDatas.put(mBean.getCode(), mBean);
        commit() ;
    }

    public void delete (SaleEnterContent mBean){
//        datas.delete(StringUtils.StringChangeToInt(mBean.getCode()));
        mapDatas.remove(mBean.getCode());
        commit() ;
    }

    public void deleteAll() {
//        datas.clear();
        mapDatas.clear();
        commit() ;
    }

    public List<SaleEnterContent> getAll(){
        return getDataFromlocal() ;
    }

    private List<SaleEnterContent>  sparseArrayToList ( ){

        int i = 0 ;
        List<SaleEnterContent> mBeanlist = new ArrayList<SaleEnterContent>();

//        for ( i = 0 ; i < datas.size() ; i++){
//
//            mBeanlist.add(datas.valueAt(i)) ;
//        }
        for(Map.Entry<String, SaleEnterContent> entry : mapDatas.entrySet()) {
            mBeanlist.add(entry.getValue());
        }
//        for(i = 0; i < mapDatas.size(); i++ ) {
//            SaleEnterContent saleEnterItem = new SaleEnterContent();
//            saleEnterItem = ObjectUtil.modelA2B(mapDatas.get(i), SaleEnterContent.class);
//            mBeanlist.add(saleEnterItem);
//        }
        return mBeanlist ;
    }

    private void listToSparseArray(){
        List<SaleEnterContent> mBeanlist = getDataFromlocal() ;
        if ( mBeanlist != null && mBeanlist.size() > 0 ){
            for (SaleEnterContent mBean : mBeanlist){
//                datas.put(StringUtils.StringChangeToInt(mBean.getCode()), mBean);
                mapDatas.put(mBean.getCode(), mBean);
            }
        }
    }

    private void commit(){
        List<SaleEnterContent> mBeanlist = sparseArrayToList ();
        mSharePreDate.setParam(Constant.PRODUCT_PROVIDER , ObjectUtil.jsonFormatter(mBeanlist));
    }

    public List<SaleEnterContent> getDataFromlocal(){
        String json = (String) mSharePreDate.getParam(Constant.PRODUCT_PROVIDER , null) ;
        LogF.d(TAG , "---"+json) ;
        List<SaleEnterContent> mBeanList = new ArrayList<>() ;
        if (json != null && json.length() > 0 ){
            mBeanList = ObjectUtil.fromJson(json , new TypeToken<List<SaleEnterContent>>(){}.getType()) ;
        }
        return mBeanList ;
    }

}
