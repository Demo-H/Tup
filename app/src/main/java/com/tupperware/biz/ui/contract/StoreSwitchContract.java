package com.tupperware.biz.ui.contract;


import com.tupperware.biz.entity.StoreBean;

/**
 * Created by dhunter on 2018/3/19.
 */

public class StoreSwitchContract {

    public interface View {
        void setStoreData(StoreBean store);
        void setRefreshStoreData(StoreBean store);
    }

    public interface Presenter {
        void getStoreData();
        void getRefreshStoreData();
    }
}
