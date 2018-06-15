package com.tupperware.huishengyi.ui.contract;

import com.tupperware.huishengyi.entity.LoveVipBean;

/**
 * Created by dhunter on 2018/3/8.
 */

public class LoveVipContract {
    public interface View {
        void setLoveVipData(LoveVipBean lovevip);
        void setMoreLoveVipData(LoveVipBean lovevip);
    }

    public interface Presenter {
        void getLoveVipData();
        void getMoreLoveVipData();
    }
}
