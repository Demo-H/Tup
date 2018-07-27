package com.tupperware.huishengyi.utils.data;

import android.content.Context;

import com.android.dhunter.common.network.SharePreferenceHelper;
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
 * Created by dhunter on 2018/6/8.
 */

public class ProductHistoryProvider {

    private static final String TAG = "ProductHistoryProvider";
    private HashMap<String, SaleEnterContent> mapHistoryDatas = null;
    private Context context = null ;
    private static ProductHistoryProvider mHistoryInstance ;
//    private SharePreferenceData mSharePreDate;
    private SharePreferenceHelper mSharePreDate;

    private ProductHistoryProvider(Context context) {
        this.context = context ;
        this.mapHistoryDatas = new HashMap<>();
        mSharePreDate = new SharePreferenceHelper(context.getApplicationContext());
        listToSparseArray() ;
    }

    public static synchronized ProductHistoryProvider getInstance(Context context){
        if(mHistoryInstance == null){
            mHistoryInstance = new ProductHistoryProvider(context);
        }
        return mHistoryInstance;
    }

    public void put (SaleEnterContent mBean){

//        SaleEnterContent temp = datas.get(StringUtils.StringChangeToInt(mBean.getCode())) ;
        SaleEnterContent temp = mapHistoryDatas.get(mBean.getCode()) ;
        LogF.d("------ ", " "+mBean.getCode()) ;

        if (temp != null){
            temp.setLocalStockNum(mBean.getLocalStockNum());
            temp.setLocalSaleNum(mBean.getLocalSaleNum());
        }else {
            temp = mBean ;
        }
//        datas.put(StringUtils.StringChangeToInt(mBean.getCode()), temp);
        mapHistoryDatas.put(mBean.getCode(), temp);
        commit() ;
    }


//    public void put (SaleEnterContent mBean){
//        this.put(mBean);
//    }

    public boolean isExitList(SaleEnterContent mBean) {
//        SaleEnterContent temp = datas.get(StringUtils.StringChangeToInt(mBean.getCode()));
        SaleEnterContent temp = mapHistoryDatas.get(mBean.getCode());
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
        return mapHistoryDatas.size();
    }

    public void update (SaleEnterContent mBean){
//        datas.put(StringUtils.StringChangeToInt(mBean.getCode()), mBean);
        mapHistoryDatas.put(mBean.getCode(), mBean);
        commit() ;
    }

    public void delete (SaleEnterContent mBean){
//        datas.delete(StringUtils.StringChangeToInt(mBean.getCode()));
        mapHistoryDatas.remove(mBean.getCode());
        commit() ;
    }

    public void deleteAll() {
//        datas.clear();
        mapHistoryDatas.clear();
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
        for(Map.Entry<String, SaleEnterContent> entry : mapHistoryDatas.entrySet()) {
            mBeanlist.add(entry.getValue());
        }
//        for(i = 0; i < mapHistoryDatas.size(); i++ ) {
//            SaleEnterContent saleEnterItem = new SaleEnterContent();
//            saleEnterItem = ObjectUtil.modelA2B(mapHistoryDatas.get(i), SaleEnterContent.class);
//            mBeanlist.add(saleEnterItem);
//        }
        return mBeanlist ;
    }

    private void listToSparseArray(){
        List<SaleEnterContent> mBeanlist = getDataFromlocal() ;
        if ( mBeanlist != null && mBeanlist.size() > 0 ){
            for (SaleEnterContent mBean : mBeanlist){
//                datas.put(StringUtils.StringChangeToInt(mBean.getCode()), mBean);
                mapHistoryDatas.put(mBean.getCode(), mBean);
            }
        }
    }

    private void commit(){
        List<SaleEnterContent> mBeanlist = sparseArrayToList ();
//        mSharePreDate.saveObjectData(Constant.PRODUCT_HISTORY_PROVIDER , ObjectUtil.jsonFormatter(mBeanlist));
        mSharePreDate.saveData(Constant.PRODUCT_HISTORY_PROVIDER , ObjectUtil.jsonFormatter(mBeanlist));
    }

    public List<SaleEnterContent> getDataFromlocal(){
//        String json = (String) mSharePreDate.getObjectData(Constant.PRODUCT_HISTORY_PROVIDER, "") ;
        String json =  mSharePreDate.getValue(Constant.PRODUCT_HISTORY_PROVIDER) ;
        LogF.d(TAG , "---"+json) ;
        List<SaleEnterContent> mBeanList = new ArrayList<>() ;
        if (json != null && json.length() > 0 ){
            mBeanList = ObjectUtil.fromJson(json , new TypeToken<List<SaleEnterContent>>(){}.getType()) ;
        }
        return mBeanList ;
    }

}
