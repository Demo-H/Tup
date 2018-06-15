package com.tupperware.huishengyi.ui.presenter;

import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.ui.contract.MemberDetialContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/31.
 */

public class MemberDetialPresenter extends BasePresenter implements MemberDetialContract.Presenter {

    private static final String TAG = "MemberDetialPresenter";

    private OrderDataManager mDataManager;
    private MemberDetialContract.View mView;

    @Inject
    public MemberDetialPresenter(OrderDataManager mDataManager, MemberDetialContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getMemberDetialData(long member_id) {

    }
}
