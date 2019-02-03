package com.tupperware.biz.ui.presenter;

import com.android.dhunter.common.base.BasePresenter;
import com.android.dhunter.common.network.ErrorDisposableObserver;
import com.tupperware.biz.entity.member.MemberBean;
import com.tupperware.biz.entity.member.MemberSearchConditionDTO;
import com.tupperware.biz.http.MemberDataManager;
import com.tupperware.biz.network.StateCode;
import com.tupperware.biz.ui.contract.SearchMemberContract;

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
    public void getMemberSearchData(MemberSearchConditionDTO searchCondition) {
        addDisposabe(mDataManager.getMemberList(new ErrorDisposableObserver<MemberBean>() {
            @Override
            public void onNext(MemberBean memberBean) {
                mView.hideDialog();
                if(memberBean != null) {
                    if(!memberBean.isSuccess()) {
                        mView.toast(memberBean.getMessage());
                        if(memberBean.resultCode != null && (memberBean.resultCode.equals(StateCode.TOKEN_OUT_DATE_S)
                                || memberBean.resultCode.equals(StateCode.TOKEN_OUT_DATE))) {
                            mView.reLogin();
                            mDataManager.deleteSPData();
                        } else {
                            mView.setEmptyView();
                        }
                    } else if(memberBean.getPageInfo() == null) {
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
        }, searchCondition));
    }

    @Override
    public void getMoreMemberSearchData(MemberSearchConditionDTO searchCondition) {
        addDisposabe(mDataManager.getMemberList(new ErrorDisposableObserver<MemberBean>() {
            @Override
            public void onNext(MemberBean memberBean) {
                if(memberBean != null && memberBean.isSuccess()) {
                    mView.setMoreMemberSearchData(memberBean);
                }
            }

            @Override
            public void onComplete() {

            }
        }, searchCondition));
    }
}