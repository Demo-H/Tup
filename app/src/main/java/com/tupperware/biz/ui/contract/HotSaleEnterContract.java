package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.hotsale.HotInventoryInfo;
import com.tupperware.biz.entity.hotsale.HotSaleEnterReqeust;

import java.util.List;

/**
 * Created by dhunter on 2018/12/3.
 */

public class HotSaleEnterContract {
    public interface View {
        void setHotSaleListData(List<HotInventoryInfo> list);
        void setSubmitResult();
        void toast(String msg);
        void setNormalView();
        void setNetErrorView();
        void setEmptyView();
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getHotEnterList(Integer storeId);
        void submitHotSale(HotSaleEnterReqeust requestData);
    }
}
