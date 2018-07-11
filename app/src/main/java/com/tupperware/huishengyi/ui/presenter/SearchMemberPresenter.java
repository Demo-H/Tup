package com.tupperware.huishengyi.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.huishengyi.config.Constant;
import com.tupperware.huishengyi.entity.member.MemberBean;
import com.tupperware.huishengyi.http.MemberDataManager;
import com.tupperware.huishengyi.ui.contract.SearchMemberContract;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/5/30.
 */

public class SearchMemberPresenter extends BasePresenter implements SearchMemberContract.Presenter {
    private static final String TAG = "SearchMemberFragment";

    private MemberDataManager mDataManager;
    private SearchMemberContract.View mView;

    @Inject
    public SearchMemberPresenter(MemberDataManager mDataManager, SearchMemberContract.View view) {
        this.mDataManager = mDataManager;
        this.mView = view;
    }

    @Override
    public void getMemberSearchData(String memberCode) {
        addDisposabe(mDataManager.getMemberSearchData(new ErrorDisposableObserver<MemberBean>() {
            @Override
            public void onNext(MemberBean memberBean) {
                mView.hideDialog();
                if(memberBean != null) {
                    if(!memberBean.isSuccess()) {
                        mView.toast(memberBean.getMessage());
                        mView.setEmptyView();
                    } else if(memberBean.getModels() == null || memberBean.getModels().isEmpty()) {
                        mView.setEmptyView();
                    } else {
                        mView.setNormalView();
                        mView.setMemberSearchData(memberBean);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.hideDialog();
                mView.setNetErrorView();
            }

            @Override
            public void onComplete() {

            }
        },memberCode, Constant.FIRST_PAGE_INDEX));

    }

    @Override
    public void getMoreMemberSearchData(String memberCode, int pageIndex) {
        addDisposabe(mDataManager.getMemberSearchData(new ErrorDisposableObserver<MemberBean>() {
            @Override
            public void onNext(MemberBean memberBean) {
                if(memberBean != null && memberBean.isSuccess()) {
                    mView.setMoreMemberSearchData(memberBean);
                }
            }

            @Override
            public void onComplete() {

            }
        },memberCode, pageIndex));
    }
}