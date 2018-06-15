package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.saleenter.PostEnterBean;

/**
 * Created by dhunter on 2018/6/5.
 */

public class EnterListContract {

    public interface View {
        void toast(String msg);
        void postSuccess();
    }

    public interface Presenter {
        void postSaleList(PostEnterBean postEnterBean);
    }

}
