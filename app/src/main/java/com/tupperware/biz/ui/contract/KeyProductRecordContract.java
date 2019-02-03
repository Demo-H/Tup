package com.tupperware.biz.ui.contract;


import com.tupperware.biz.entity.KeyProductRecordBean;

/**
 * Created by dhunter on 2018/3/22.
 */

public class KeyProductRecordContract {
    public interface View {
        void setKeyProductRecordData(KeyProductRecordBean keyProductRecordBean);
    }

    public interface Presenter {
        void getKeyProductRecordData();
    }
}
