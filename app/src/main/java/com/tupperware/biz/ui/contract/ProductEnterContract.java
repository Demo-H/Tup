package com.tupperware.biz.ui.contract;


import com.tupperware.biz.entity.ProductEnterBean;

/**
 * Created by dhunter on 2018/3/23.
 */

public class ProductEnterContract {

    public interface View {
        void setProductEnterData(ProductEnterBean productEnterBean);
    }

    public interface Presenter {
        void getProductEnterData();
    }
}
