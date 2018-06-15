package com.tupperware.huishengyi.ui.contract;


import com.tupperware.huishengyi.entity.ProductEnterBean;

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
