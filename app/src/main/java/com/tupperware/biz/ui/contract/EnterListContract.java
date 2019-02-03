package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.saleenter.PostEnterBean;

/**
 * Created by dhunter on 2018/6/5.
 */

public class EnterListContract {

    public interface View {
        void toast(String msg);
        void postSuccess();
        void reLogin();
    }

    public interface Presenter {
        void postSaleList(PostEnterBean postEnterBean);
    }

}
