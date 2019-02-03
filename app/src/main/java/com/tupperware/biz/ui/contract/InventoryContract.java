package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.hotsale.HotInventoryResponse;

/**
 * Created by dhunter on 2018/11/30.
 */

public class InventoryContract {
    public interface View {
        void setHotInventoryData(HotInventoryResponse response);
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getHotInventory(Integer storeId, String time);
    }
}
