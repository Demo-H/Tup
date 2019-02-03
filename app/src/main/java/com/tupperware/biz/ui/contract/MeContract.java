package com.tupperware.biz.ui.contract;

import com.tupperware.biz.entity.MeBenfitCoin;

/**
 * Created by dhunter on 2018/9/6.
 */

public class MeContract {
    public interface View {
        void setMeData(MeBenfitCoin bean);
        void toast(String msg);
        void hideDialog();
        void reLogin();
    }

    public interface Presenter {
        void getMeData(Integer storeId);
    }
}
