package com.tupperware.huishengyi.ui.presenter;

import com.tupperware.huishengyi.http.OrderDataManager;
import com.tupperware.huishengyi.ui.contract.SearchMemberContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/30.
 */

public class SearchMemberPresenter extends BasePresenter implements SearchMemberContract.Presenter {
    private static final String TAG = "SearchMemberFragment";

    private OrderDataManager mDataManager;
    private SearchMemberContract.View mView;

    @Inject
    public SearchMemberPresenter(OrderDataManager mDataManager, SearchMemberContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getMemberSearchData(String memberCode) {

    }
}